package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb

import android.database.Cursor

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Artist

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor) : Artist?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): Artist? =
        with(cursor) {
            if (moveToNext()) {
                Artist(
                    name=getString(cursor.getColumnIndexOrThrow(ARTIST_COLUMN)),
                    description=getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                    wikipediaURL=getString(cursor.getColumnIndexOrThrow(ARTIST_URL_COLUMN))
                )
            } else {
                null
            }
        }

}
