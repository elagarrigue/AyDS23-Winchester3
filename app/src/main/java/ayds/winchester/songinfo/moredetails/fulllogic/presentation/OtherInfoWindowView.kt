package ayds.winchester.songinfo.moredetails.fulllogic.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindow

interface OtherInfoWindowView {

}

class OtherInfoWindowViewImpl(val presenter:Presenter):OtherInfoWindowView,AppCompatActivity() {
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var logoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProperties()
        val artistName = getArtistNameFromIntent()
        openArtistInfoWindow(artistName)
    }

    private fun initProperties() {
        setContentView(R.layout.activity_other_info)
        artistDescriptionTextView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun getArtistNameFromIntent() = intent.getStringExtra(OtherInfoWindow.ARTIST_NAME_EXTRA).toString()

    private fun openArtistInfoWindow(artistName:String) {
        Thread {
            presenter.showArtistInfo(artistName)
        }.start()
    }

}