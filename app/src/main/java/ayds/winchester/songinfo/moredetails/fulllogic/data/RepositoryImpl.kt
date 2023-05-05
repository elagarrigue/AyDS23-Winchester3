package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import java.io.IOException

internal class RepositoryImpl : Repository {

    private fun searchArtistInfo(artistName: String): ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

   override private fun getArtistInfoFromRepository(artistName: String): ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist? {
        val artist = searchArtistInfo(artistName)
        saveArtistInfo(artist)
        return artist
    }

    private fun saveArtistInfo(artist: ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist?) {
        artist?.let{
            dataBase.saveArtist(artist.name, it.description)
        }
    }

    private fun searchArtistInfo(artistName: String): ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

     private fun ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }
}