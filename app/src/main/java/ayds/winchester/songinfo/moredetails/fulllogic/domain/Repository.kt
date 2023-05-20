package ayds.winchester.songinfo.moredetails.fulllogic.domain

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist

interface Repository {
    fun getArtistInfo(artistName:String): Artist?
}