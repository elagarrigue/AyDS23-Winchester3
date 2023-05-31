package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

import android.database.Cursor

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor) : Collection<Card>
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): Collection<Card> {
       val cards = mutableListOf<Card>()
       with(cursor) {
           while (moveToNext()) {
               val values = Source.values()
               val sourceInt = getInt(cursor.getColumnIndexOrThrow(SOURCE_COLUMN))
               cards.add(Card(
                   source = values[sourceInt],
                   description = getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                   infoURL = getString(cursor.getColumnIndexOrThrow(ARTIST_URL_COLUMN)),
                   sourceLogoURL = getString(cursor.getColumnIndexOrThrow(SOURCE_LOGO_URL_COLUMN))
               ))
           }
       }
       return cards
   }

}
