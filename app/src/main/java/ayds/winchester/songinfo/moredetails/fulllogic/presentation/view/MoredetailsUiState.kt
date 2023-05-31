package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

private const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

data class UICard(
    var source: String,
    var infoURL: String,
    var sourceLogoURL: String,
    var description: String
)

data class MoreDetailsUIState(
    val cards: Collection<UICard> = emptyList()
)