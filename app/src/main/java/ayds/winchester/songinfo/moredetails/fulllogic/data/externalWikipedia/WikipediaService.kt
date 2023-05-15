package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist

interface WikipediaService{
    fun getArtist(artistName: String): WikipediaArtist?
}