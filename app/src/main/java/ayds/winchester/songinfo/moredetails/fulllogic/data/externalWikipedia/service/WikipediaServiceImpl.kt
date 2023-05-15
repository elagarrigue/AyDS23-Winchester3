package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import retrofit2.Response

class WikipediaServiceImpl(
    private val wikipediaToArtistsResolver: WikipediaToArtistsResolver,
    private val wikipediaAPI : WikipediaAPI
) : WikipediaService {

    override fun getArtist(artistName: String): WikipediaArtist? {
        val callResponse = getArtistInfoFromAPI(artistName)
        return wikipediaToArtistsResolver.getArtistFromExternalData(callResponse.body())
    }

    private fun getArtistInfoFromAPI(artistName: String?): Response<String?> =
        wikipediaAPI.getArtistInfo(artistName).execute()

}