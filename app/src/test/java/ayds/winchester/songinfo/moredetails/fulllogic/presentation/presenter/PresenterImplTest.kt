package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter

import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoreDetailsUIState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class PresenterImplTest {
    private var artistRepository: Repository = mockk(relaxUnitFun = true)
    private var formatter: ArtistDescriptionFormatterHtml = mockk(relaxUnitFun = true)
    private var presenter: Presenter = mockk(relaxUnitFun = true)
    @Before
    fun setup(){
        presenter=PresenterImpl(artistRepository,formatter)
    }

    @Test
    fun `on showArtistInfo it should notify new uiState`() {
        val artistName = "Artist Name"
        val card=Card(infoURL = "wikiUrl", description = "")
        val formattedDescription = "Description"
        every { artistRepository.getArtistInfo(artistName) } returns card
        every { formatter.formatDescription(card) } returns formattedDescription
        val artistTester: (MoreDetailsUIState) -> Unit = mockk(relaxed = true)
        presenter.uiStateObservable.subscribe{
            artistTester(it)
        }

        presenter.showArtistInfo(artistName)

        val expectedUiState = MoreDetailsUIState(description = "Description", urlOpenButton = "wikiUrl")
        verify { artistTester(expectedUiState) }
    }

}