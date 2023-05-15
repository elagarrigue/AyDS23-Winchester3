package ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import com.google.gson.Gson
import com.google.gson.JsonObject

interface WikipediaToArtistsResolver{
    fun getArtistFromExternalData(wikipediaData: String?): WikipediaArtist?
}

private const val SNIPPET = "snippet"
private const val BASE_URL = "https://en.wikipedia.org/?curid="
private const val PAGE_ID = "pageid"
private const val QUERY = "query"
private const val SEARCH = "search"

internal class WikipediaToArtistsResolverImpl : WikipediaToArtistsResolver {

    override fun getArtistFromExternalData(wikipediaData: String?): WikipediaArtist? {
        val query = wikipediaData.getFirstItem()
        return query[SNIPPET]?.let {
            WikipediaArtist(description=query.getDescription(), wikipediaURL=query.getWikipediaUrl())
        }
    }

    private fun JsonObject.getDescription() = this[SNIPPET].asString

    private fun JsonObject.getWikipediaUrl() = BASE_URL + this[PAGE_ID]

    private fun String?.getFirstItem(): JsonObject {
        val jsonObject = Gson().fromJson(this, JsonObject::class.java)
        val query = jsonObject[QUERY].asJsonObject
        val item = query[SEARCH].asJsonArray
        return item[0].asJsonObject
    }
}