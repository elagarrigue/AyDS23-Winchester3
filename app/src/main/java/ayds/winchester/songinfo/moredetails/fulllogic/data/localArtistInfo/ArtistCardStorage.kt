package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist


interface ArtistCardStorage {

    fun getArtistCards(artistName: String): List<CardArtist>
    fun saveCards(cardArtist: CardArtist, artistName: String)
}