package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.Broker
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import java.io.IOException

internal class RepositoryImpl(
    private val artistLocalStorage : ArtistLocalStorage,
    private val broker: Broker
): Repository {

    override fun getArtistInfo(artistName: String): Collection<Card> {
        var artist = artistLocalStorage.getArtistCards(artistName)
        when {
            artist != null ->  artist.markArtistAsLocal()
            else -> {
                try{
                    val wikipediaArtist:WikipediaArtist? = wikipediaService.getArtist(artistName)
                    artist = wikipediaArtist?.mapWikipediaArtist()
                    artist?.let{
                        artistLocalStorage.saveArtist(artist)
                    }
                }catch (e1: IOException) {
                }
            }
        }
        //TODO obtener lista de la base de datos o servicios externos
        return artist
    }

    private fun Card.markArtistAsLocal() {
        this.isLocallyStored = true
    }

    private fun WikipediaArtist.mapWikipediaArtist():Card{
        return Card(
                this.name,
                this.wikipediaURL,
                false,
                this.description
            )
    }
}