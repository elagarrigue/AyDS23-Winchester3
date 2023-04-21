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

class DataBase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
     try{
         db.execSQL(createArtistInfoTableQuery)
         Log.i("DataBaseArtist", "ArtistDataBase Created OK")
     }catch ( error : IOException){
         Log.i("DataBaseArtist", "ArtisDataBase error : "+ error.message);
     }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
      /*  fun testDB() {
            var connection: Connection? = null
            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db")
                val statement = connection.createStatement()
                statement.queryTimeout = 30 // set timeout to 30 sec.

                //statement.executeUpdate("drop table if exists person");
                //statement.executeUpdate("create table person (id integer, name string)");
                //statement.executeUpdate("insert into person values(1, 'leo')");
                //statement.executeUpdate("insert into person values(2, 'yui')");
                val rs = statement.executeQuery("select * from artists")
                while (rs.next()) {
                    // read the result set
                    println("id = " + rs.getInt("id"))
                    println("artist = " + rs.getString("artist"))
                    println("info = " + rs.getString("info"))
                    println("source = " + rs.getString("source"))
                }
            } catch (e: SQLException) {
                // if the error message is "out of memory",
                // it probably means no database file is found
                System.err.println(e.message)
            } finally {
                try {
                    connection?.close()
                } catch (e: SQLException) {
                    // connection close failed.
                    System.err.println(e)
                }
            }
        }*/



        @JvmStatic
        fun saveArtist(artist: String?, info: String?) {
            // Gets the data repository in write mode
            // val db = dbHelper.writableDatabase

// Create a new map of values, where column names are the keys
            val values = ContentValues()
            values.put(ARTIST_COLUMN, artist)
            values.put(INFO_COLUMN, info)
            values.put(SOURCE_COLUMN, 1)

// Insert the new row, returning the primary key value of the new row
            writableDatabase?.insert(ARTIST_COLUMN, null, values)
        }



        @JvmStatic
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

}
