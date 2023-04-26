package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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
            "$INFO_COLUMN string" +
            "$SOURCE_COLUMN)"

private const val DATABASE_NAME="dictionary.db"
private const val DATABASE_VERSION= 1

class DataBase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
         try{
             db.execSQL(createArtistInfoTableQuery)
            Log.i("DataBaseArtist", "ArtistDataBase Created OK")
        }catch ( error : IOException){
            Log.e("DataBaseArtist", "ArtisDataBase error : "+ error.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {

        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist)
        values.put(INFO_COLUMN, info)
        values.put(SOURCE_COLUMN, 1)

        writableDatabase?.insert(ARTISTS_TABLE, null, values)
    }

    fun getInfo(artist: String): String? {

        val cursor = readableDatabase.query(
            ARTISTS_TABLE,
            projection,
            "$ARTIST_COLUMN = ?",
            arrayOf(artist),
            null,
            null,
            "artist DESC"
        )
        return mapArtistInfo(cursor)
    }

    private fun mapArtistInfo(cursor: Cursor): String? {
        val items: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val info = cursor.getString(
                cursor.getColumnIndexOrThrow("info")
            )
            items.add(info)
        }
        cursor.close()
        return if (items.isEmpty()) null else items[0]
    }
}
