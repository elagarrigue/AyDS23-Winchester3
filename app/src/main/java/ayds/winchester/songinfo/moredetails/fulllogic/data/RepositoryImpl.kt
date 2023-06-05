package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.CardBroker
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import java.io.IOException

internal class RepositoryImpl(
    private val artistLocalStorage : ArtistLocalStorage,
    private val cardBroker: CardBroker
): Repository {

    override fun getArtistInfo(artistName: String): Collection<CardArtist> {
        var artistCardInfo = artistLocalStorage.getArtistCards(artistName)
        when {
            artistCardInfo.isNotEmpty() -> artistCardInfo.map { artistCardInfo ->
                artistCardInfo.markArtistAsLocal();
            }
            else -> {
                try {
                    artistCardInfo = cardBroker.getArtistFromExternalServices(artistName);
                    artistCardInfo.forEach{ artistCard ->
                            artistLocalStorage.saveArtist(artistCard, artistName)
                        }
                } catch (e1: IOException) {}
            }
        }
        return artistCardInfo
    }

    private fun CardArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }

}