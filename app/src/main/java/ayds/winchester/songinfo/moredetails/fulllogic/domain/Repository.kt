package ayds.winchester.songinfo.moredetails.fulllogic.domain

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface Repository {
    fun getArtistInfo(artistName:String): Collection<Card>
}