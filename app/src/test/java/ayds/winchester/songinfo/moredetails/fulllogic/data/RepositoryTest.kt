package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException


internal class RepositoryTest {
    private val artistLocalStorage: ArtistLocalStorage = mockk(relaxUnitFun = true)
    private val wikipediaService: ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService = mockk(relaxUnitFun = true)
    private val artistRepository: Repository by lazy {
        RepositoryImpl(artistLocalStorage, wikipediaService)
    }

    @Test
    fun `given an existing artist in the local repository it should return it and mark it as local`() {
        val artistName = "name"
        val card = Card(name = artistName, infoURL = "wikiUrl", description = "desc")
        every { artistLocalStorage.getArtistCards(artistName) } returns card

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(card, result)
        assertTrue(card.isLocallyStored)
    }

    @Test
    fun `given an artist that is not in the local repository but can be obtained from the external service, it should return the song, store it but not mark it as local`(){
        val artistName = "name"
        val card = Card(name = artistName, infoURL = "wikiUrl", description = "desc")
        every { artistLocalStorage.getArtistCards(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } returns card

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(card, result)
        verify { artistLocalStorage.saveArtist(card) }
        assertFalse(card.isLocallyStored)
    }

    @Test
    fun `given an artist that cannot be obtained in the local repository or external service it should return null and not store it`(){
        val artistName = "name"
        every { artistLocalStorage.getArtistCards(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } returns null

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(null, result)
        verify(exactly = 0) { artistLocalStorage.saveArtist(any()) }
    }

    @Test
    fun `given service exception should return null`(){
        val artistName = "name"
        every { artistLocalStorage.getArtistCards(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } throws mockk<IOException>()

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(null, result)
        verify(exactly = 0) { artistLocalStorage.saveArtist(any()) }
    }
}