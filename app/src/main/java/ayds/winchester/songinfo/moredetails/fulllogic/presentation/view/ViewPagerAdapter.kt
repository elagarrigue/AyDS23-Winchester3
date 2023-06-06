package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ayds.winchester.songinfo.R
import com.squareup.picasso.Picasso

class ArtistViewPagerAdapter(private val artistCards: List<UICard>, private val activity: AppCompatActivity) : RecyclerView.Adapter<ArtistCardViewHolder>(){

    override fun getItemCount() = artistCards.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistCardViewHolder {
        return ArtistCardViewHolder.from(parent, activity)
    }

    override fun onBindViewHolder(cardHolder: ArtistCardViewHolder, position: Int) {
        cardHolder.bindArtistInfoToComponents(artistCards.elementAt(position))
    }

}

class ArtistCardViewHolder private constructor(private val cardView: View, private val activity: AppCompatActivity):RecyclerView.ViewHolder(cardView){
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var logoImageView: ImageView
    private lateinit var sourceTextView: TextView

    init {
        initProperties()
    }

    private fun initProperties(){
        artistDescriptionTextView = cardView.findViewById(R.id.textPane2)
        openUrlButton = cardView.findViewById(R.id.openUrlButton)
        logoImageView = cardView.findViewById(R.id.imageView)
        sourceTextView = cardView.findViewById(R.id.sourceLabel)
    }

    fun bindArtistInfoToComponents(artistCard: UICard) {
        loadWikipediaLogo(artistCard.sourceLogoURL)
        setSourceLabel(artistCard.source)
        updateArtistDescription(artistCard.description)
        setButtonUrl(artistCard.infoURL)
    }

    private fun setSourceLabel(source: String) {
        activity.runOnUiThread {
            sourceTextView.text = source
        }
    }

    private fun loadWikipediaLogo(urlImage: String) {
        activity.runOnUiThread {
            Picasso.get().load(urlImage).into(logoImageView)
        }
    }

    private fun updateArtistDescription(finalText: String?) {
        activity.runOnUiThread {
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
        activity.startActivity(intent)
    }

    companion object {
        fun from(parent: ViewGroup, activity: AppCompatActivity): ArtistCardViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.viewpager_card, parent, false)
            return ArtistCardViewHolder(view, activity)
        }
    }

}

