package ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb

import android.database.Cursor
import ayds.winchester.songinfo.home.model.repository.local.spotify.sqldb.CursorToSpotifySongMapper

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.WikipediaArtist

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor) : WikipediaArtist?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): WikipediaArtist? =
        with(cursor) {
            if (moveToNext()) {
                WikipediaArtist(
                    name=getString(cursor.getColumnIndexOrThrow(ARTIST_COLUMN)),
                    description=getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                    wikipediaURL=getString(cursor.getColumnIndexOrThrow(ARTIST_URL_COLUMN))
                )
            } else {
                null
            }
        }

}
