package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb

import android.database.Cursor

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface CursorToArtistMapper{
    fun mapArtistInfo(cursor: Cursor) : Collection<Card>
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

   override fun mapArtistInfo(cursor: Cursor): Collection<Card> {
       //TODO ciclar sobre el cursor para armar la coleccion de cards.
       var cards = mutableListOf<Card>()
       with(cursor) {
           if (moveToNext()) {
               cards.add(Card(
                   source = getString(cursor.getColumnIndexOrThrow(ARTIST_COLUMN)),
                   description = getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                   infoURL = getString(cursor.getColumnIndexOrThrow(ARTIST_URL_COLUMN)),
                   sourceLogoURL = getString(cursor.getColumnIndexOrThrow(SOURCE_LOGO_URL_COLUMN))
               ))
           } else {
               null
           }
       }
       return cards
   }

}
