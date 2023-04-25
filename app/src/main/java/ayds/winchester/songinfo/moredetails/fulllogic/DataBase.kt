package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

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

private const val DATABASE_NAME="artist.db" //"dictionary.db"
private const val DATABASE_VERSION= 1

private var dataBase: DataBase = TODO()

class DataBase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        dataBase = this
         try{
             db.execSQL(createArtistInfoTableQuery)
            Log.i("DataBaseArtist", "ArtistDataBase Created OK")
        }catch ( error : IOException){
            Log.e("DataBaseArtist", "ArtisDataBase error : "+ error.message);
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
       fun testDB() {
            var connection: Connection? = null
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:./$DATABASE_NAME")
                val statement = connection.createStatement()
                statement.queryTimeout = 30

                val rs = statement.executeQuery("select * from $ARTISTS_TABLE")
                while (rs.next()) {
                    Log.i("DataBaseArtist", "$ID_COLUMN = " + rs.getInt(ID_COLUMN))
                    Log.i("DataBaseArtist","$ARTIST_COLUMN = " + rs.getString(ARTIST_COLUMN))
                    Log.i("DataBaseArtist","$INFO_COLUMN = " + rs.getString(INFO_COLUMN))
                    Log.i("DataBaseArtist","$SOURCE_COLUMN = " + rs.getString(SOURCE_COLUMN))
                }
            } catch (error: SQLException) {
                Log.e("DataBaseArtist", "ArtisDataBase error: "+error.message)
            } finally {
                try {
                    connection?.close()
                } catch (error: SQLException) {
                    Log.e("DataBaseArtist", "ArtisDataBase error: "+error.message)
                }
            }
        }

        @JvmStatic
        fun saveArtist(artist: String?, info: String?) {

            val values = ContentValues()
            values.put(ARTIST_COLUMN, artist)
            values.put(INFO_COLUMN, info)
            values.put(SOURCE_COLUMN, 1)

            dataBase.writableDatabase?.insert(ARTIST_COLUMN, null, values)
        }

        @JvmStatic
        fun getInfo(artist: String): String? {

            val cursor = dataBase.readableDatabase.query(
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

}
