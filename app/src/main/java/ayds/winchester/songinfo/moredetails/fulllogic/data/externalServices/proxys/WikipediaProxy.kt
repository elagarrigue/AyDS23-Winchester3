package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService
import java.io.IOException

class WikipediaProxy(
    private val wikipediaService : WikipediaService,
): ProxyService {

    override fun getCard(artistName: String):CardArtist?=
    try {
        wikipediaService.getArtist(artistName)?.mapWikipediaArtist()
    } catch (e1: IOException) {
        null
    }

    private fun WikipediaArtist?.mapWikipediaArtist():CardArtist?=
        this?.let{
            CardArtist(
                Source.WIKIPEDIA,
                this.wikipediaURL,
                WIKIPEDIA_LOGO_URL,
                this.description
            )
        }
}

