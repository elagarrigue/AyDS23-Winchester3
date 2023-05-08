package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observer
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindow
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindowUIState
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.Presenter
import com.squareup.picasso.Picasso

interface OtherInfoWindowView {

}

class OtherInfoWindowViewImpl(val presenter: Presenter):OtherInfoWindowView,AppCompatActivity() {
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var logoImageView: ImageView
    private val observer: Observer<OtherInfoWindowUIState> =
        Observer { value ->
            updateUIComponents(value)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProperties()
        initObservers()
        val artistName = getArtistNameFromIntent()
        openArtistInfoWindow(artistName)
    }

    private fun initProperties() {
        setContentView(R.layout.activity_other_info)
        artistDescriptionTextView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initObservers() {
        presenter.uiStateObservable.subscribe(observer)
    }

    private fun updateUIComponents(uiState: OtherInfoWindowUIState) {
        loadWikipediaLogo(uiState.urlImage)
        updateArtistDescription(uiState.description)
        setButtonUrl(uiState.urlOpenButton)
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

    private fun getArtistNameFromIntent() = intent.getStringExtra(OtherInfoWindow.ARTIST_NAME_EXTRA).toString()

    private fun openArtistInfoWindow(artistName:String) {
        Thread {
            presenter.showArtistInfo(artistName)
        }.start()
    }

}