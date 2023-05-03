package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*

private const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
private const val QUERY = "query"
private const val SEARCH = "search"
private const val SNIPPET = "snippet"
private const val PAGE_ID = "pageid"
private const val BASE_URL = "https://en.wikipedia.org/?curid="
private const val IS_LOCALLY_STORED_PREFIX = "[*]"
private const val IS_NOT_LOCALLY_STORED_PREFIX = ""
private const val NO_RESULTS_MESSAGE = "No Results"
private const val HTML_DIV_DESCRIPTION = "<html><div width=400>"
private const val HTML_FONT = "<font face=\"arial\""
private const val HTML_CLOSE_TAGS = "</font></div></html>"
private const val NEW_LINE_PLAIN = "\n"
private const val NEW_LINE_HTML = "<br>"
private const val SINGLE_QUOTE = "'"
private const val BLANK_SPACE = " "
private const val BOLD_TAG = "<b>"
private const val CLOSE_BOLD_TAG = "</b>"
private const val BASE_URL_RETROFIT = "https://en.wikipedia.org/w/"
private const val SLASH_NEW_LINE = "\\n"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var logoImageView: ImageView
    private lateinit var dataBase: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProperties()
        openArtistInfoWindow()
    }

    private fun getArtistNameFromIntent() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()

    private fun initProperties() {
        setContentView(R.layout.activity_other_info)
        artistDescriptionTextView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
        dataBase = DataBase(this)
    }

    private fun openArtistInfoWindow() {
        Thread {
            showArtistInfo()
        }.start()
    }

    private fun showArtistInfo() {
        val artist = getArtistInfoFromRepository()
        val uiState = createUIState(artist)
        updateUIComponents(uiState)
    }

    private fun createUIState(artist: WikipediaArtist?): UIState {
        val description = formatArtistInfo(artist)
        return UIState(description, artist?.wikipediaURL)
    }

    private fun getArtistInfoFromRepository(): WikipediaArtist? {
        val artistName = getArtistNameFromIntent()
        val artist = searchArtistInfo(artistName)
        saveArtistInfo(artist)
        return artist
    }

    private fun updateUIComponents(uiState: UIState) {
        loadWikipediaLogo(uiState.urlImage)
        updateArtistDescription(uiState.description)
        setButtonUrl(uiState.urlOpenButton)
    }

    private fun formatArtistInfo(artist: WikipediaArtist?): String {
        return when(artist){
            is WikipediaArtist ->
                    (if (artist.isLocallyStored) IS_LOCALLY_STORED_PREFIX else IS_NOT_LOCALLY_STORED_PREFIX) +
                    formatDescription(artist.description, artist.name)

            else -> NO_RESULTS_MESSAGE
        }
    }

    private fun saveArtistInfo(artist: WikipediaArtist?) {
        artist?.let{
            dataBase.saveArtist(artist.name, it.description)
        }
    }

    private fun formatDescription(description: String, artistName: String): String {
        val text = description.replace(SLASH_NEW_LINE, NEW_LINE_PLAIN)
        return textToHtml(text, artistName)
    }

    private fun searchArtistInfo(artistName: String): WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

    private fun getArtistFromWikipedia(artistName: String): WikipediaArtist? {
        val callResponse = getArtistInfoFromAPI(artistName)
        val callResponseBody = callResponse.body()
        val artist = getArtistFromExternalData(callResponseBody, artistName)
        return artist
    }

    private fun getArtistFromExternalData(wikipediaData: String?,artistName: String): WikipediaArtist? {
        val query = wikipediaData.getFirstItem()
        return query[SNIPPET]?.let {
            WikipediaArtist(name=artistName, description=query.getDescription(), wikipediaURL=query.getWikipediaUrl())
        }
    }

    private fun JsonObject.getDescription() = this[SNIPPET].asString

    private fun JsonObject.getWikipediaUrl() = BASE_URL + this[PAGE_ID]

    private fun WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }

    private fun getArtistFromLocalStorage(artistName: String): WikipediaArtist? {
        val artistDescription = dataBase.getInfo(artistName)
        return artistDescription?.let{
            WikipediaArtist(name=artistName,description=artistDescription)
        }
    }

    private fun getArtistInfoFromAPI(artistName: String?): Response<String> {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_RETROFIT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun String?.getFirstItem(): JsonObject {
        val jsonObject = Gson().fromJson(this, JsonObject::class.java)
        val query = jsonObject[QUERY].asJsonObject
        val item = query[SEARCH].asJsonArray
        return item[0].asJsonObject
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append(HTML_DIV_DESCRIPTION)
        builder.append(HTML_FONT)
        val textWithBold = text
            .replace(SINGLE_QUOTE, BLANK_SPACE)
            .replace(NEW_LINE_PLAIN, NEW_LINE_HTML)
            .replace("(?i)$term".toRegex(), BOLD_TAG + term!!.uppercase(Locale.getDefault()) + CLOSE_BOLD_TAG)
        builder.append(textWithBold)
        builder.append(HTML_CLOSE_TAGS)
        return builder.toString()
    }

    private fun setButtonUrl(url:String?) {
        openUrlButton.setOnClickListener {
            openExternalUrl(url)
        }
    }

    private fun openExternalUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun loadWikipediaLogo(urlImage: String) {
        runOnUiThread {
            Picasso.get().load(urlImage).into(logoImageView)
        }
    }

    private fun updateArtistDescription(finalText: String?) {
        runOnUiThread {
            artistDescriptionTextView.text = Html.fromHtml(finalText)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}

data class WikipediaArtist(
    val name: String,
    var wikipediaURL: String = BASE_URL,
    var isLocallyStored: Boolean = false,
    var description: String
)

data class UIState(
    val description: String,
    val urlOpenButton: String? = BASE_URL,
    val urlImage: String = WIKIPEDIA_LOGO_URL,
)