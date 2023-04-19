package ayds.winchester.songinfo.moredetails.fulllogic

import android.database.sqlite.SQLiteDatabase
import ayds.winchester.songinfo.home.model.entities.Song.SpotifySong

interface IArtistWikiDataBase {

    fun saveArtist(db: SQLiteDatabase, artist: String, info: String)

    fun getInfo(db: SQLiteDatabase, artist: SpotifySong)


}