package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

private const val WIKIPEDIA_LOGO_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

data class MoredetailsUIState(
    val cards: Collection<Card> = emptyList()
)