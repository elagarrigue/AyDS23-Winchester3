package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist


interface ArtistLocalStorage {

    fun getArtistCards(artistName: String): Collection<CardArtist>
    fun saveArtist(cardArtist: CardArtist, artistName: String)
}