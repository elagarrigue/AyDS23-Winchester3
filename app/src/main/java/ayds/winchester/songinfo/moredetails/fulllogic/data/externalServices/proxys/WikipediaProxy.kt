package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

private const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

class WikipediaProxy(
    private val wikipediaService : WikipediaService,
): ProxyService {

    override fun getArtistInfo(artistName: String):Card?=
        wikipediaService.getArtist(artistName)?.mapWikipediaArtist()

    private fun WikipediaArtist?.mapWikipediaArtist():Card?=
        this?.let{
            Card(
                "Wikipedia",
                this.wikipediaURL,
                WIKIPEDIA_LOGO_URL,
                this.description
            )
        }
}

