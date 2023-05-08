package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import java.io.IOException

internal class RepositoryImpl
    (
    private val artistLocalStorage : ArtistLocalStorage,
    private val wikipediaService : WikipediaService
    ): Repository {

    private fun searchArtistInfo(artistName: String): WikipediaArtist? {
        var wikipediaArtist = artistLocalStorage.getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = wikipediaService.getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

   override fun getArtistInfoFromRepository(artistName: String): WikipediaArtist? {
        val artist = searchArtistInfo(artistName)
        saveArtistInfo(artist)
        return artist
    }

    private fun saveArtistInfo(artist: WikipediaArtist?) {
        artist?.let{
            artistLocalStorage.saveArtist(artist.name, it.description)
        }
    }


    private fun WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }
}