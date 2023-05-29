package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.UICard


interface Presenter {
    val uiStateObservable: Observable<MoredetailsUIState>
    var uiState: MoredetailsUIState
    fun showArtistInfo(artistName:String)
}

class PresenterImpl(private val artistRepository: Repository, private val formatter: ArtistDescriptionFormatterHtml):Presenter {
    override var uiState = MoredetailsUIState()
    private val onUIStateSubject = Subject<MoredetailsUIState>()
    override val uiStateObservable:Observable<MoredetailsUIState> = onUIStateSubject

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
        val formattedCards = cards.map { card ->
            val sourceTitle = getSourceTitle(card)
            val formattedDescription = formatter.formatDescription(card, artistName)
            UICard(
                source = sourceTitle,
                infoURL = card.infoURL,
                sourceLogoURL = card.sourceLogoURL,
                description = formattedDescription
            )
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