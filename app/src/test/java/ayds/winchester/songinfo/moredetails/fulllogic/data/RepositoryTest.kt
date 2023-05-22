package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException


internal class RepositoryTest {
    private val artistLocalStorage: ArtistLocalStorage = mockk(relaxUnitFun = true)
    private val wikipediaService: WikipediaService = mockk(relaxUnitFun = true)
    private val artistRepository: Repository by lazy {
        RepositoryImpl(artistLocalStorage, wikipediaService)
    }

    @Test
    fun `given an existing artist in the local repository it should return it and mark it as local`() {
        val artistName = "name"
        val artist = WikipediaArtist(name = artistName, wikipediaURL = "wikiUrl", description = "desc")
        every { artistLocalStorage.getArtist(artistName) } returns artist

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given an artist that is not in the local repository but can be obtained from the external service, it should return the song, store it but not mark it as local`(){
        val artistName = "name"
        val artist = WikipediaArtist(name = artistName, wikipediaURL = "wikiUrl", description = "desc")
        every { artistLocalStorage.getArtist(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } returns artist

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(artist, result)
        verify { artistLocalStorage.saveArtist(artist) }
        assertFalse(artist.isLocallyStored)
    }

    @Test
    fun `given an artist that cannot be obtained in the local repository or external service it should return null and not store it`(){
        val artistName = "name"
        every { artistLocalStorage.getArtist(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } returns null

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(null, result)
        verify(exactly = 0) { artistLocalStorage.saveArtist(any()) }
    }

    @Test
    fun `given service exception should return null`(){
        val artistName = "name"
        every { artistLocalStorage.getArtist(artistName) } returns null
        every { wikipediaService.getArtist(artistName) } throws mockk<IOException>()

        val result = artistRepository.getArtistInfo(artistName)

        assertEquals(null, result)
        verify(exactly = 0) { artistLocalStorage.saveArtist(any()) }
    }
}