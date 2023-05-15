package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist


interface Presenter {
    val uiStateObservable: Observable<MoredetailsUIState>
    fun showArtistInfo(artistName:String)
}

class PresenterImpl(private val artistRepository: Repository, private val formatter: ArtistDescriptionFormatterHtml):Presenter {
    private var uiState = MoredetailsUIState()
    private val onUIStateSubject = Subject<MoredetailsUIState>()
    override val uiStateObservable:Observable<MoredetailsUIState> = onUIStateSubject

    override fun showArtistInfo(artistName:String) {
        val artist = artistRepository.getArtistInfoFromRepository(artistName)
        updateUIState(artist)
        onUIStateSubject.notify(uiState)
    }

    private fun updateUIState(artist: WikipediaArtist?) {
        val description = formatter.formatDescription(artist)
        uiState = uiState.copy(description = description, urlOpenButton = artist?.wikipediaURL)
    }

}