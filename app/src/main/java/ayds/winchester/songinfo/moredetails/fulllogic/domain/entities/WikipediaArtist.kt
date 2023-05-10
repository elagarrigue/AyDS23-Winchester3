package ayds.winchester.songinfo.moredetails.fulllogic.domain.entities

data class WikipediaArtist(
    val name: String,
    var wikipediaURL: String,
    var isLocallyStored: Boolean = false,
    var description: String
)

