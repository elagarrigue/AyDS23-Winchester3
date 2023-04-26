package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
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
private const val PAGEID = "pageid"
private const val BASE_URL = "https://en.wikipedia.org/?curid="

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var dataBase: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistDescriptionTextView = findViewById(R.id.textPane2)
        open(intent.getStringExtra(ARTIST_NAME_EXTRA).toString())
    }

    private fun open(artist: String) {
        dataBase = DataBase(this)
        getArtistInfo(artist)
    }

    private fun getArtistInfo(artistName: String) {
        Thread {
            val artist = searchArtistInfo(artistName)
            val description = formatArtistInfo(artist)
            displayWindow(description, artist?.wikipediaURL)
        }.start()
    }

    private fun displayWindow(
        description: String,
        url:String?
    ) {
        loadWikipediaLogo()
        updateArtistDescription(description)
        setButtonUrl(url)
    }

    private fun formatArtistInfo(artist: WikipediaArtist?): String {
        return when(artist){
            is WikipediaArtist ->
                if (artist.isLocallyStored) "[*]" else "" +
                formatDescription(artist.description, artist.name)

            else -> "No Results"
        }
    }

    private fun formatDescription(description: String, artistName: String): String {
        val text = description.replace("\\n", "\n")
        return textToHtml(text, artistName)
    }

    private fun searchArtistInfo(artistName: String): WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                    wikipediaArtist?.let{
                        dataBase.saveArtist(artistName, it.description)
                    }
                }catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
        return wikipediaArtist
    }

    private fun getArtistFromWikipedia(artistName: String): WikipediaArtist? {
        val callResponse = getArtistInfoFromAPI(artistName)
        return getArtistFromExternalData(callResponse.body(),artistName)
    }

    private fun getArtistFromExternalData(wikipediaData: String?,artistName: String): WikipediaArtist? {
        val query = wikipediaData.getFirstItem()
        return if (query[SNIPPET]==null){
            null
        }else{
            WikipediaArtist(name=artistName, description=query.getDescription(), wikipediaURL=query.getWikipediaUrl())
        }
    }

    private fun JsonObject.getDescription() = this[SNIPPET].asString

    private fun JsonObject.getWikipediaUrl() = BASE_URL + this[PAGEID]

    private fun WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }

    private fun getArtistFromLocalStorage(artistName: String): WikipediaArtist? {
        val artistDescription = dataBase.getInfo(artistName)
        return if (artistDescription == null)
            null
        else
            WikipediaArtist(name=artistName,description=artistDescription)
    }

    private fun getArtistInfoFromAPI(artistName: String?): Response<String> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
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
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace("(?i)$term".toRegex(), "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>")
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }

    private fun setButtonUrl(url:String?) {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    private fun loadWikipediaLogo() {
        runOnUiThread {
            Picasso.get().load(WIKIPEDIA_LOGO_URL).into(findViewById<View>(R.id.imageView) as ImageView)
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


