package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import java.io.IOException

internal class RepositoryImpl(
    private val artistLocalStorage : ArtistLocalStorage,
    private val wikipediaService : WikipediaService
): Repository {

    override fun getArtistInfo(artistName: String): WikipediaArtist? {
        var wikipediaArtist = artistLocalStorage.getArtist(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = wikipediaService.getArtist(artistName)
                    wikipediaArtist?.let{
                        artistLocalStorage.saveArtist(wikipediaArtist)
                    }
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

    private fun WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }
}