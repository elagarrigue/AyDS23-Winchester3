package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb

import android.database.Cursor
import ayds.winchester.songinfo.home.model.repository.local.spotify.sqldb.CursorToSpotifySongMapper

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor) : String?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): String? =
        with(cursor) {
            if (moveToNext()) {
                getString(cursor.getColumnIndexOrThrow(INFO_COLUMN))
            } else {
                null
            }
        }

}
