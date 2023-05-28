package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import com.squareup.picasso.Picasso

class ViewPagerAdapterImpl(private val artistCards:List<Card>) : RecyclerView.Adapter<ViewPagerAdapterImpl.ArtistCardViewHolder>(){

    inner class ArtistCardViewHolder(cardView: View):RecyclerView.ViewHolder(cardView){
        var artistDescriptionTextView: TextView = cardView.findViewById(R.id.textPane2)
        var openUrlButton: Button = cardView.findViewById(R.id.openUrlButton)
        var logoImageView: ImageView = cardView.findViewById(R.id.imageView)
        var sourceTextView: TextView = cardView.findViewById(R.id.sourceLabel)

        init {

        }

    }

    override fun getItemCount() = artistCards.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistCardViewHolder {
        return ArtistCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_card, parent, false))
    }

    override fun onBindViewHolder(holder: ArtistCardViewHolder, position: Int) {
        val artistCard = artistCards[position]
        holder.artistDescriptionTextView.text = artistCard.description
        holder.sourceTextView.text = artistCard.source
        holder.openUrlButton.setOnClickListener {
            openExternalUrl(artistCard.infoURL)
        }
        Picasso.get().load(artistCard.sourceLogoURL).into(holder.logoImageView)
    }

    private fun openExternalUrl(url: String?) { //TODO navegar a external url
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }



}


