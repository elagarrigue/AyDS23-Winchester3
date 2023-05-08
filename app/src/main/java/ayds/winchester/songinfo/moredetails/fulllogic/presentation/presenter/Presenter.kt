package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindowUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist


interface Presenter {
    fun showArtistInfo(artistName:String)
}

class PresenterImpl(private val artistRepository: Repository, val formatter: ArtistDescriptionFormatterHtml):Presenter {

    override fun showArtistInfo(artistName:String) {
        val artist = artistRepository.getArtistInfoFromRepository(artistName)
        val uiState = createUIState(artist)
    }

    private fun createUIState(artist: WikipediaArtist?): OtherInfoWindowUIState {
        val description = formatter.formatDescription(artist)
        return OtherInfoWindowUIState(description, artist?.wikipediaURL)
    }

}