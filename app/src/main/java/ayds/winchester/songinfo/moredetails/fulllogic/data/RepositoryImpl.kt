package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist
import java.io.IOException

internal class RepositoryImpl(
    private val artistLocalStorage : ArtistLocalStorage,
    private val wikipediaService : WikipediaService
): Repository {

    override fun getArtistInfo(artistName: String): Artist? {
        var artist = artistLocalStorage.getArtist(artistName)
        when {
            artist != null ->  artist.markArtistAsLocal()
            else -> {
                try{
                    val wikipediaArtist:WikipediaArtist? = wikipediaService.getArtist(artistName)
                    artist = mapWikipediaArtist(wikipediaArtist)
                    artist?.let{
                        artistLocalStorage.saveArtist(artist)
                    }
                }catch (e1: IOException) {
                }
            }
        }
        return artist
    }

    private fun Artist.markArtistAsLocal() {
        this.isLocallyStored = true
    }

    private fun mapWikipediaArtist(submoduleArtist: WikipediaArtist?):Artist?{
        return submoduleArtist?.let{
            Artist(
                submoduleArtist.name,
                submoduleArtist.wikipediaURL,
                false,
                submoduleArtist.description
            )
        }
    }
}