package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import org.junit.Assert
import org.junit.Test

internal class  CardDescriptionFormatterHtmlTest {

   private val artistdescriptionformat by lazy {CardDescriptionFormatterHtml()}

    @Test
    fun `given a locally stored wikipedia artist it should return the format description`() {
        val card : CardArtist = CardArtist(
            name =  "Duki",
            infoURL = "https://en.wikipedia.org/?curid=64829658",
            isLocallyStored = true,
            description = "Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap"
        )

        val result = artistdescriptionformat.formatDescription(card);
        val expected = "[*] <html><div width=400><font face=\"arial\">Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non locally stored wikipedia artist it should return the format description`() {
        val card : CardArtist = CardArtist(
            name =  "Duki",
            infoURL = "https://en.wikipedia.org/?curid=64829658",
            isLocallyStored = false,
            description = "Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap"
        )

        val result = artistdescriptionformat.formatDescription(card);
        val expected = " <html><div width=400><font face=\"arial\">Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap</font></div></html>"

        Assert.assertEquals(expected, result)
    }
    @Test
    fun `given a non wikipedia artist it should return not found description`() {
        val card : CardArtist? = null;
        val result = artistdescriptionformat.formatDescription(card);
        val expected = "No Results"

        Assert.assertEquals(expected, result)
    }
}