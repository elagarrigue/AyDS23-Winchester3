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

    private val onUIStateSubject = Subject<MoredetailsUIState>()
    override val uiStateObservable:Observable<MoredetailsUIState> = onUIStateSubject

    override fun showArtistInfo(artistName:String) {
        val artist = artistRepository.getArtistInfoFromRepository(artistName)
        val uiState = createUIState(artist)
        onUIStateSubject.notify(uiState)
    }

    private fun createUIState(artist: WikipediaArtist?): MoredetailsUIState {
        val description = formatter.formatDescription(artist)
        return MoredetailsUIState(description, artist?.wikipediaURL)
    }

}