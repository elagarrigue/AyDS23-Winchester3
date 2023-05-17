package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.fulllogic.data.RepositoryImpl
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsUIState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class PresenterImplTest {
    private var artistRepository: Repository = mockk(relaxUnitFun = true)
    private var formatter: ArtistDescriptionFormatterHtml = mockk(relaxUnitFun = true)
    private lateinit var uiStateObservable: Observable<MoredetailsUIState>
    private var presenter: Presenter = mockk(relaxUnitFun = true) {
        PresenterImpl(artistRepository,formatter)
        every { uiStateObservable } returns uiStateObservable
    }

    @Test
    fun `showArtistInfo updates UI state with artist information`() {
        val artistName = "Artist Name"
        val artist = WikipediaArtist(
            name = "Artist Name",
            wikipediaURL = "https://en.wikipedia.org/artist",
            description = "Description"
        )
        val formattedDescription = "Formatted Description"

        every { artistRepository.getArtistInfo(artistName) } returns artist
        every { formatter.formatDescription(artist) } returns formattedDescription

        val uiStateObserver = mockk<Observer<MoredetailsUIState>>(relaxed = true)
        presenter.uiStateObservable.subscribe(uiStateObserver)

        presenter.showArtistInfo(artistName)

        verify { artistRepository.getArtistInfo(artistName) }
        verify { formatter.formatDescription(artist) }
        verify { uiStateObserver.update(any()) }

        val expectedUiState = MoredetailsUIState(description = formattedDescription, urlOpenButton = artist.wikipediaURL)

        verify { uiStateObserver.update(expectedUiState) }
    }

    @Test
    fun `showArtistInfo updates UI state with empty artist information`() {
        val artistName = "Artist Name"
        val emptySong = null

        every { artistRepository.getArtistInfo(artistName) } returns emptySong

        val uiStateObserver = mockk<Observer<MoredetailsUIState>>(relaxed = true)
        presenter.uiStateObservable.subscribe(uiStateObserver)

        presenter.showArtistInfo(artistName)

        val expectedUiState = MoredetailsUIState(description = "", urlOpenButton = "")

        verify { uiStateObserver.update(expectedUiState) }
    }

}