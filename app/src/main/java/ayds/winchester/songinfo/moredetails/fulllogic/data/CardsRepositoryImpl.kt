package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.CardBroker
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistCardStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.CardsRepository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

internal class CardsRepositoryImpl(
    private val artistCardStorage : ArtistCardStorage,
    private val cardsBroker: CardBroker
): CardsRepository {

    override fun getArtistCards(artistName: String): List<CardArtist> {
        var artistCardsInfo = artistCardStorage.getArtistCards(artistName)
        when {
            artistCardsInfo.isNotEmpty() -> artistCardsInfo.map { artistCardInfo ->
                artistCardInfo.markArtistAsLocal()
            }
            else -> {
                artistCardsInfo = cardsBroker.getArtistFromExternalServices(artistName)
                artistCardsInfo.forEach{ artistCard ->
                    artistCardStorage.saveCards(artistCard, artistName)
                }
            }
        }
        return artistCardsInfo
    }

    private fun CardArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }

}