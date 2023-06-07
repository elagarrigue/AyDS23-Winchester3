package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoreDetailsUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.CardsRepository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.UICard

private const val EMPTY_CARD_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/f/f3/Exclamation_mark.png"

interface MoreDetailsPresenter {
    val uiStateObservable: Observable<MoreDetailsUIState>
    var uiState: MoreDetailsUIState
    fun showArtistInfo(artistName:String)
}

private const val NO_RESULT_MESSAGE = "No Result"

internal class MoreDetailsPresenterImpl(private val artistCardsRepository: CardsRepository, private val formatter: CardDescriptionFormatterHtml):MoreDetailsPresenter {
    override var uiState = MoreDetailsUIState()
    private val onUIStateSubject = Subject<MoreDetailsUIState>()
    override val uiStateObservable:Observable<MoreDetailsUIState> = onUIStateSubject

    override fun showArtistInfo(artistName:String) {
        Thread {
            displayArtistInfo(artistName)
        }.start()
    }

    private fun displayArtistInfo(artistName: String) {
        val artistInfoCards = artistCardsRepository.getArtistCards(artistName)
        val formattedCards = formatArtistCards(artistInfoCards, artistName)
        updateUIState(formattedCards)
        onUIStateSubject.notify(uiState)
    }

    private fun updateUIState(cards: List<UICard>) {
        uiState = uiState.copy(cards = cards)
    }

    private fun formatArtistCards(cardArtists: List<CardArtist>, artistName: String): List<UICard> {
        val formattedCards: List<UICard>
        if(cardArtists.isEmpty()){
            val emptyCard = UICard("", "", EMPTY_CARD_IMAGE_URL, NO_RESULT_MESSAGE)
            formattedCards = listOf(emptyCard)
        }else {
            formattedCards = cardArtists.map { card ->
                val sourceTitle = getSourceTitle(card)
                val formattedDescription = formatter.formatDescription(card, artistName)
                UICard(
                    source = sourceTitle,
                    infoURL = card.infoURL,
                    sourceLogoURL = card.sourceLogoURL,
                    description = formattedDescription
                )
            }
        }
        return formattedCards
    }

    private fun getSourceTitle(cardArtist: CardArtist): String {
        return when (cardArtist.source) {
            Source.WIKIPEDIA -> "Wikipedia"
            Source.LAST_FM -> "Last FM"
            Source.NEW_YORK_TIMES -> "New York Times"
        }
    }

}