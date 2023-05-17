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
import ayds.winchester.songinfo.moredetails.fulllogic.MoredetailsInjector
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.Presenter
import com.squareup.picasso.Picasso

interface MoredetailsView {
    fun setPresenter(presenter: Presenter)
}

class MoredetailsViewImpl:MoredetailsView, AppCompatActivity() {
    private lateinit var presenter: Presenter
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var logoImageView: ImageView
    private val observer: Observer<MoredetailsUIState> =
        Observer { value ->
            updateUIComponents(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initModule()
        initProperties()
        initObservers()
        initWindow()
    }

    private fun initWindow() {
        val artistName = getArtistNameFromIntent()
        openArtistInfoWindow(artistName)
    }

    override fun setPresenter(presenter: Presenter) {
        this.presenter = presenter
    }

    private fun initModule(){
        MoredetailsInjector.init(this)
    }

    private fun initProperties() {
        artistDescriptionTextView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initObservers() {
        presenter.uiStateObservable.subscribe(observer)
    }

    private fun updateUIComponents(uiState: MoredetailsUIState) {
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

    private fun getArtistNameFromIntent() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()

    private fun openArtistInfoWindow(artistName:String) {
        presenter.showArtistInfo(artistName)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}