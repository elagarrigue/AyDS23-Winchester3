package ayds.winchester.songinfo.moredetails.fulllogic.domain.entities

data class Card(
    var source: String,
    var infoURL: String,
    var sourceLogoURL: String,
    var description: String,
    var isLocallyStored: Boolean,
)

//TODO que sucede con el isLocallyStored?

