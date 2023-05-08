package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import retrofit2.Response

class WikipediaServiceImpl(
    private val wikipediaToArtistsResolver: WikipediaToArtistsResolver,
    private val wikipediaAPI : WikipediaAPI
) : WikipediaService {

    override fun getArtistFromWikipedia(artistName: String): WikipediaArtist? {
        val callResponse = getArtistInfoFromAPI(artistName)
        val callResponseBody = callResponse.body()
        return wikipediaToArtistsResolver.getArtistFromExternalData(callResponseBody, artistName)
    }

    private fun getArtistInfoFromAPI(artistName: String?): Response<String?> {
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }
}