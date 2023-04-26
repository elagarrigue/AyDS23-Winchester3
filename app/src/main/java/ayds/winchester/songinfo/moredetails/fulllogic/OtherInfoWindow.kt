package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
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
    private var dataBase: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistDescriptionTextView = findViewById(R.id.textPane2)
        open(intent.getStringExtra("artistName"))
    }

    private fun open(artist: String?) {
        dataBase = DataBase(this)
        dataBase.saveArtist("test", "sarasa")
        Log.e("TAG", "" + dataBase.getInfo("test"))
        Log.e("TAG", "" + dataBase.getInfo("nada"))
        getArtistInfo(artist)
    }

    private fun getArtistInfo(artistName: String?) {
        Log.e("TAG", "artistName $artistName")
        Thread {
            var artistDescription = dataBase.getInfo(artistName)
            if (artistDescription != null) {
                artistDescription = "[*]$artistDescription"
            } else {
                try {
                    val callResponse = getArtistInfoFromAPI(artistName)
                    Log.e("JSON ", callResponse.body().toString())
                    val query = getFirstItem(callResponse)
                    artistDescription = getFormatTextSnippet(query[SNIPPET], artistName)
                    setButtonUrl(query[PAGEID])
                } catch (e1: IOException) {
                    Log.e("TAG", "Error $e1")
                    e1.printStackTrace()
                }
            }
            Log.e("TAG", "Get Image from $WIKIPEDIA_LOGO_URL")
            loadWikipediaLogo()
            updateArtistDescription(artistDescription)
        }.start()
    }

    private fun getArtistInfoFromAPI(artistName: String?): Response<String> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun getFirstItem(callResponse: Response<String>): JsonObject {
        val jsonObject = Gson().fromJson(callResponse.body(), JsonObject::class.java)
        val query = jsonObject[QUERY].asJsonObject
        val item = query[SEARCH].asJsonArray
        return item[0].asJsonObject
    }

    private fun getFormatTextSnippet(
        snippet: JsonElement?,
        artistName: String?
    ): String {
        var text: String
        if (snippet == null) {
            text = "No Results"
        } else {
            text = snippet.asString.replace("\\n", "\n")
            text = textToHtml(text, artistName)
            dataBase.saveArtist(artistName, text)
        }
        return text
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

    private fun setButtonUrl(pageid: JsonElement?) {
        val urlString = "$BASE_URL$pageid"
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
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