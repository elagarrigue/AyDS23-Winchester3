package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist


interface ArtistLocalStorage {

    fun getArtist(artistName: String): Artist?
    fun saveArtist(artist: Artist)
}