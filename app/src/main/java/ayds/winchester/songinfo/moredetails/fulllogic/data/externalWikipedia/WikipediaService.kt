package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist

interface WikipediaService{
    fun getArtistFromWikipedia(artistName: String): WikipediaArtist?
}