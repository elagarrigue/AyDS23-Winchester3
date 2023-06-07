package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

data class UICard(
    var source: String,
    var infoURL: String,
    var sourceLogoURL: String,
    var description: String
)

data class MoreDetailsUIState(
    val cards: List<UICard> = emptyList()
)