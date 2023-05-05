package ayds.winchester.songinfo.moredetails.fulllogic.domain

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist


interface Repository {
    fun getArtistInfo(artistName:String): WikipediaArtist?
}