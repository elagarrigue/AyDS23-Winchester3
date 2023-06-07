package ayds.winchester.songinfo.moredetails.fulllogic.domain.entities

enum class Source {
    WIKIPEDIA,
    LAST_FM,
    NEW_YORK_TIMES
}

data class CardArtist(
    var source: Source,
    var infoURL: String,
    var sourceLogoURL: String,
    var description: String,
    var isLocallyStored: Boolean = false,
)


