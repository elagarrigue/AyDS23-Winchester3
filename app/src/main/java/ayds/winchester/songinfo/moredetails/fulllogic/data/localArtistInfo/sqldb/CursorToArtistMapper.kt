package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

import android.database.Cursor

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor): List<CardArtist>
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): List<CardArtist> {
       val cardArtists = mutableListOf<CardArtist>()
       with(cursor) {
           while (moveToNext()) {
               val values = Source.values()
               val sourceInt = getInt(cursor.getColumnIndexOrThrow(SOURCE_COLUMN))
               cardArtists.add(CardArtist(
                   source = values[sourceInt],
                   description = getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                   infoURL = getString(cursor.getColumnIndexOrThrow(ARTIST_URL_COLUMN)),
                   sourceLogoURL = getString(cursor.getColumnIndexOrThrow(SOURCE_LOGO_URL_COLUMN))
               ))
           }
       }
       return cardArtists
   }

}
