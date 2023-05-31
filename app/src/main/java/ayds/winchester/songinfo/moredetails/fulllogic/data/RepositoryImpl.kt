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
        var artistCardInfo = artistLocalStorage.getArtistCards(artistName)
        when {
            artistCardInfo.isNotEmpty() -> artistCardInfo.map { artistCardInfo ->
                artistCardInfo.markArtistAsLocal();
            }
            else -> {
                try {
                    artistCardInfo = broker.getArtistFromExternalServices(artistName);
                    artistCardInfo.let {
                        artistCardInfo.map { artist ->
                            artistLocalStorage.saveArtist(artist, artistName)
                        }
                    }
                } catch (e1: IOException) {}
            }
        }
        //TODO obtener lista de la base de datos o servicios externos
        return artistCardInfo
    }

    private fun Card.markArtistAsLocal() {
        this.isLocallyStored = true
    }

}