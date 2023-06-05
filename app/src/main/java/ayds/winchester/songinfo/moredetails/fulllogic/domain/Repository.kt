package ayds.winchester.songinfo.moredetails.fulllogic.domain

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

interface Repository {
    fun getArtistInfo(artistName:String): Collection<CardArtist>
}