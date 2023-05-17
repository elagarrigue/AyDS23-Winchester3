package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.observer.Observable
import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsUIState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class PresenterImplTest {
    private lateinit var presenter: PresenterImpl
    private lateinit var artistRepository: Repository
    private lateinit var formatter: ArtistDescriptionFormatterHtml
    private lateinit var uiStateObservable: Observable<MoredetailsUIState>

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