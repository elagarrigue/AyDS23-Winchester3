package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

private const val DATABASE_NAME="dictionary.db"
private const val DATABASE_VERSION= 1

class ArtistLocalStorageImpl(context: Context? , private val cursorToArtistMapper: CursorToArtistMapper ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), ArtistLocalStorage{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistInfoTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun saveArtist(card: Card, artistName: String) {
        ContentValues().apply {
            put(ARTIST_COLUMN, artistName)
            put(SOURCE_LOGO_URL_COLUMN, card.sourceLogoURL)
            put(INFO_COLUMN, card.description)
            put(ARTIST_URL_COLUMN, card.infoURL)
            put(SOURCE_COLUMN, card.source.ordinal)
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

    override fun getArtistCards(artistName: String): Collection<Card> {
        val cursor = getArtistCursor(artistName)
        return cursorToArtistMapper.mapArtistInfo(cursor)
    }
}
