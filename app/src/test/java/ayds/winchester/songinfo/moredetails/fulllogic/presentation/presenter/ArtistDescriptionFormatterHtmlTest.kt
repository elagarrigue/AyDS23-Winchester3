package ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist
import org.junit.Assert
import org.junit.Test

internal class  ArtistDescriptionFormatterHtmlTest {

   private val artistdescriptionformat by lazy {ArtistDescriptionFormatterHtml()}

    @Test
    fun `given a locally stored wikipedia artist it should return the format description`() {
        val artist : Artist = Artist(
            name =  "Duki",
            wikipediaURL = "https://en.wikipedia.org/?curid=64829658",
            isLocallyStored = true,
            description = "Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap"
        )

        val result = artistdescriptionformat.formatDescription(artist);
        val expected = "[*] <html><div width=400><font face=\"arial\">Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap</font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non locally stored wikipedia artist it should return the format description`() {
        val artist : Artist = Artist(
            name =  "Duki",
            wikipediaURL = "https://en.wikipedia.org/?curid=64829658",
            isLocallyStored = false,
            description = "Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap"
        )

        val result = artistdescriptionformat.formatDescription(artist);
        val expected = " <html><div width=400><font face=\"arial\">Gonzalo Julián Conde (born 29 August 1998), known professionally as <span class=\"searchmatch\">Bizarrap</span>, is an Argentine DJ and record producer. He specializes in EDM, Latin trap</font></div></html>"

        Assert.assertEquals(expected, result)
    }
    @Test
    fun `given a non wikipedia artist it should return not found description`() {
        val artist : Artist? = null;
        val result = artistdescriptionformat.formatDescription(artist);
        val expected = "No Results"

        Assert.assertEquals(expected, result)
    }
}