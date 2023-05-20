package ayds.winchester.songinfo.moredetails.fulllogic.domain.entities

data class Artist(
    var name: String = "",
    var wikipediaURL: String,
    var isLocallyStored: Boolean = false,
    var description: String
)

