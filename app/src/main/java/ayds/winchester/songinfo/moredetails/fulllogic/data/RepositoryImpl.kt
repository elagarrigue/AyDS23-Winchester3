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
            artist != null -> artist.map { artist ->
                artist.markArtistAsLocal();
            }
            else -> {
                try {
                    val artistExternalInfo: Collection<Card> =
                        broker.getArtistFromExternalServices(artistName);
                    artist = artistExternalInfo.mapCardArtist()
                    artist.let {
                        artist.map { artist ->
                            artistLocalStorage.saveArtist(artist, artistName)
                        }
                    }
                } catch (e1: IOException) {
                }
            }
        }
        //TODO obtener lista de la base de datos o servicios externos
        return artist
    }

    private fun Card.markArtistAsLocal() {
        this.isLocallyStored = true
    }

    private fun Collection<Card>.mapCardArtist(): Collection<Card> {
        return this.map { artist ->
            Card(
                artist.source,
                artist.infoURL,
                artist.sourceLogoURL,
                artist.description,
                false,
            )
        }


    }
}