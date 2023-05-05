package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

