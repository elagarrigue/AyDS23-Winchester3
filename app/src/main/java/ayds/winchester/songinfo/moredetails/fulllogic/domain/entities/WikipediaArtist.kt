package ayds.winchester.songinfo.moredetails.fulllogic.domain.entities

private const val BASE_URL = "https://en.wikipedia.org/?curid="

data class WikipediaArtist(
    val name: String,
    var wikipediaURL: String = BASE_URL,
    var isLocallyStored: Boolean = false,
    var description: String
)