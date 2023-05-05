package ayds.winchester.songinfo.moredetails.fulllogic.presentation
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindowUIState
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import java.util.*

private const val IS_LOCALLY_STORED_PREFIX = "[*]"
private const val IS_NOT_LOCALLY_STORED_PREFIX = ""
private const val NO_RESULTS_MESSAGE = "No Results"
private const val HTML_DIV_DESCRIPTION = "<html><div width=400>"
private const val HTML_FONT = "<font face=\"arial\">"
private const val HTML_CLOSE_TAGS = "</font></div></html>"
private const val NEW_LINE_PLAIN = "\n"
private const val NEW_LINE_HTML = "<br>"
private const val SINGLE_QUOTE = "'"
private const val BLANK_SPACE = " "
private const val BOLD_TAG = "<b>"
private const val CLOSE_BOLD_TAG = "</b>"
private const val SLASH_NEW_LINE = "\\n"

interface Presenter {
    fun showArtistInfo(artistName:String)
}

class PresenterImpl(val artistRepository: Repository):Presenter {

    override fun showArtistInfo(artistName:String) {
        val artist = artistRepository.getArtistInfo(artistName)
        val uiState = createUIState(artist)
    }

    private fun createUIState(artist: WikipediaArtist?): OtherInfoWindowUIState {
        val description = formatArtistInfo(artist)
        return OtherInfoWindowUIState(description, artist?.wikipediaURL)
    }

    private fun formatArtistInfo(artist: WikipediaArtist?): String {
        return when(artist){
            is WikipediaArtist ->
                (if (artist.isLocallyStored) IS_LOCALLY_STORED_PREFIX else IS_NOT_LOCALLY_STORED_PREFIX) +
                        formatDescription(artist.description, artist.name)

            else -> NO_RESULTS_MESSAGE
        }
    }

    private fun formatDescription(description: String, artistName: String): String {
        val text = description.replace(SLASH_NEW_LINE, NEW_LINE_PLAIN)
        return textToHtml(text, artistName)
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append(HTML_DIV_DESCRIPTION)
        builder.append(HTML_FONT)
        val textWithBold = text
            .replace(SINGLE_QUOTE, BLANK_SPACE)
            .replace(NEW_LINE_PLAIN, NEW_LINE_HTML)
            .replace("(?i)$term".toRegex(), BOLD_TAG + term!!.uppercase(Locale.getDefault()) + CLOSE_BOLD_TAG)
        builder.append(textWithBold)
        builder.append(HTML_CLOSE_TAGS)
        return builder.toString()
    }

}