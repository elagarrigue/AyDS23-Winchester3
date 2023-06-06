package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter

import ayds.winchester.songinfo.moredetails.fulllogic.domain.CardsRepository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoreDetailsUIState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

internal class MoreDetailsPresenterImplTest {
    private var artistCardsRepository: CardsRepository = mockk(relaxUnitFun = true)
    private var formatter: CardDescriptionFormatterHtml = mockk(relaxUnitFun = true)
    private var moreDetailsPresenter: MoreDetailsPresenter = mockk(relaxUnitFun = true)
    @Before
    fun setup(){
        moreDetailsPresenter=MoreDetailsPresenterImpl(artistCardsRepository,formatter)
    }

    @Test
    fun `on showArtistInfo it should notify new uiState`() {
        val artistName = "Artist Name"
        val card=CardArtist(infoURL = "wikiUrl", description = "")
        val formattedDescription = "Description"
        every { artistCardsRepository.getArtistInfo(artistName) } returns card
        every { formatter.formatDescription(card) } returns formattedDescription
        val artistTester: (MoreDetailsUIState) -> Unit = mockk(relaxed = true)
        moreDetailsPresenter.uiStateObservable.subscribe{
            artistTester(it)
        }

        moreDetailsPresenter.showArtistInfo(artistName)

        val expectedUiState = MoreDetailsUIState(description = "Description", urlOpenButton = "wikiUrl")
        verify { artistTester(expectedUiState) }
    }

}