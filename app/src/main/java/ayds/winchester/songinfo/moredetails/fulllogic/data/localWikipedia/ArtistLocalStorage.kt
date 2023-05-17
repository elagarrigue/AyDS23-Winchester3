package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist


interface ArtistLocalStorage {

    fun getArtist(artistName: String): WikipediaArtist?
    fun saveArtist(artist: WikipediaArtist)
}