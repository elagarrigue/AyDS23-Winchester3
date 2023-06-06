package ayds.winchester.songinfo.moredetails.fulllogic.domain

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

interface CardsRepository {
    fun getArtistCards(artistName:String): List<CardArtist>
}