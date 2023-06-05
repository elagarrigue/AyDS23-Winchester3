package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

private const val DATABASE_NAME="dictionary.db"
private const val DATABASE_VERSION= 1

class ArtistLocalStorageImpl(context: Context? , private val cursorToArtistMapper: CursorToArtistMapper ) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), ArtistLocalStorage{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistInfoTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun saveArtist(cardArtist: CardArtist, artistName: String) {
        ContentValues().apply {
            put(ARTIST_COLUMN, artistName)
            put(SOURCE_LOGO_URL_COLUMN, cardArtist.sourceLogoURL)
            put(INFO_COLUMN, cardArtist.description)
            put(ARTIST_URL_COLUMN, cardArtist.infoURL)
            put(SOURCE_COLUMN, cardArtist.source.ordinal)
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

    override fun getArtistCards(artistName: String): Collection<CardArtist> {
        val cursor = getArtistCursor(artistName)
        return cursorToArtistMapper.mapArtistInfo(cursor)
    }
}
