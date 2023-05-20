package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist

private const val DATABASE_NAME="dictionary.db"
private const val DATABASE_VERSION= 2

class ArtistLocalStorageImpl(context: Context? , private val cursorToArtistMapper: CursorToArtistMapper ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), ArtistLocalStorage{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistInfoTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun saveArtist(artist: Artist) {
        ContentValues().apply {
            put(ARTIST_COLUMN, artist.name)
            put(INFO_COLUMN, artist.description)
            put(ARTIST_URL_COLUMN, artist.wikipediaURL)
            put(SOURCE_COLUMN, 1)
            writableDatabase?.insert(ARTISTS_TABLE, null, this)
        }
    }

    private fun getArtistCursor(artist: String) =
        readableDatabase.query(
            ARTISTS_TABLE,
            projection,
            "$ARTIST_COLUMN = ?",
            arrayOf(artist),
            null,
            null,
            "artist DESC")

    override fun getArtist(artistName: String): Artist? {
        val cursor = getArtistCursor(artistName)
        return cursorToArtistMapper.mapArtistInfo(cursor)
    }
}
