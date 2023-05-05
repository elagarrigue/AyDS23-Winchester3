package ayds.winchester.songinfo.moredetails.fulllogic.data

import ayds.winchester.songinfo.moredetails.fulllogic.domain.Repository
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist
import java.io.IOException

private const val ARTISTS_TABLE = "artists"
private const val ID_COLUMN = "id"
private const val ARTIST_COLUMN = "artist"
private const val INFO_COLUMN = "info"
private const val SOURCE_COLUMN = "source"
private val projection = arrayOf(
    ID_COLUMN,
    ARTIST_COLUMN,
    INFO_COLUMN,
)
private const val createArtistInfoTableQuery: String =
    "create table $ARTISTS_TABLE (" +
            "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$ARTIST_COLUMN string," +
            "$INFO_COLUMN string," +
            "$SOURCE_COLUMN string)"
private const val DATABASE_NAME="dictionary.db"
private const val DATABASE_VERSION= 1

internal class RepositoryImpl : Repository {

    private fun searchArtistInfo(artistName: String): WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

   override private fun getArtistInfoFromRepository(artistName: String): ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist? {
        val artist = searchArtistInfo(artistName)
        saveArtistInfo(artist)
        return artist
    }

    private fun saveArtistInfo(artist: ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist?) {
        artist?.let{
            dataBase.saveArtist(artist.name, it.description)
        }
    }

    private fun searchArtistInfo(artistName: String): ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist? {
        var wikipediaArtist = getArtistFromLocalStorage(artistName)
        when {
            wikipediaArtist != null ->  wikipediaArtist.markArtistAsLocal()
            else -> {
                try{
                    wikipediaArtist = getArtistFromWikipedia(artistName)
                }catch (e1: IOException) {
                }
            }
        }
        return wikipediaArtist
    }

     private fun ayds.winchester.songinfo.moredetails.fulllogic.WikipediaArtist.markArtistAsLocal() {
        this.isLocallyStored = true
    }
}