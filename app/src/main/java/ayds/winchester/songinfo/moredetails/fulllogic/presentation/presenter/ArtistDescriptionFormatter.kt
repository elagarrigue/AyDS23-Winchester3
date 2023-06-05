package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
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

interface ArtistDescriptionFormatter {

    fun formatDescription(cardArtist: CardArtist?, artistName: String):String
}

class ArtistDescriptionFormatterHtml:ArtistDescriptionFormatter {

    override fun formatDescription(cardArtist: CardArtist?, artistName: String): String {
        return when(cardArtist){
            is CardArtist ->
                "${if(cardArtist.isLocallyStored) IS_LOCALLY_STORED_PREFIX else IS_NOT_LOCALLY_STORED_PREFIX} " +
                "${artistInfoToHtml(cardArtist.description, artistName)}"
            else -> NO_RESULTS_MESSAGE
        }
    }

    private fun artistInfoToHtml(description: String, artistName: String): String {
        val textWithBold = formatArtistText(description, artistName)
        val builder = StringBuilder()
        return builder
            .append(HTML_DIV_DESCRIPTION)
            .append(HTML_FONT)
            .append(textWithBold)
            .append(HTML_CLOSE_TAGS)
            .toString()
    }

    private fun formatArtistText(description: String, artistName: String): String {
        return description
            .replace(SLASH_NEW_LINE, NEW_LINE_PLAIN)
            .replace(SINGLE_QUOTE, BLANK_SPACE)
            .replace(NEW_LINE_PLAIN, NEW_LINE_HTML)
            .replace("(?i)$artistName".toRegex(), BOLD_TAG + artistName.uppercase(Locale.getDefault()) + CLOSE_BOLD_TAG)
    }
}