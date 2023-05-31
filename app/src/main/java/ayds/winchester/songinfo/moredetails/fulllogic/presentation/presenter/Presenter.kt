package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoreDetailsUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.UICard

private const val EMPTY_CARD_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/f/f3/Exclamation_mark.png"

interface Presenter {
    val uiStateObservable: Observable<MoreDetailsUIState>
    var uiState: MoreDetailsUIState
    fun showArtistInfo(artistName:String)
}

private const val NO_RESULT_MESSAGE = "No Result"

class PresenterImpl(private val artistRepository: Repository, private val formatter: ArtistDescriptionFormatterHtml):Presenter {
    override var uiState = MoreDetailsUIState()
    private val onUIStateSubject = Subject<MoreDetailsUIState>()
    override val uiStateObservable:Observable<MoreDetailsUIState> = onUIStateSubject

    override fun showArtistInfo(artistName:String) {
        Thread {
            displayArtistInfo(artistName)
        }.start()
    }

    private fun displayArtistInfo(artistName: String) {
        val artistInfoCards = artistRepository.getArtistInfo(artistName)
        val formattedCards = formatArtistCards(artistInfoCards, artistName)
        updateUIState(formattedCards)
        onUIStateSubject.notify(uiState)
    }

    private fun updateUIState(cards: Collection<UICard>) {
        uiState = uiState.copy(cards = cards)
    }

    private fun formatArtistCards(cards: Collection<Card>, artistName: String): List<UICard> {
        val formattedCards: Collection<UICard>
        if(cards.isEmpty()){
            val emptyCard = UICard("", "", EMPTY_CARD_IMAGE_URL, NO_RESULT_MESSAGE)
            formattedCards = listOf(emptyCard)
        }else {
            formattedCards = cards.map { card ->
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

    private fun getSourceTitle(card: Card): String {
        return when (card.source) {
            Source.WIKIPEDIA -> "Wikipedia"
            Source.LAST_FM -> "Last FM"
            Source.NEW_YORK_TIMES -> "New York Times"
        }
    }

}