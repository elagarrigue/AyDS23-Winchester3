package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object WikipediaServiceInjector {

    private const val BASE_URL_RETROFIT = "https://en.wikipedia.org/w/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_RETROFIT)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
    private val wikipediaToSongResolver: WikipediaToArtistsResolver = JsonArtistsResolver()

    val wikipediaService : WikipediaService = WikipediaServiceImpl(
        wikipediaToSongResolver,
        wikipediaAPI
    )
}