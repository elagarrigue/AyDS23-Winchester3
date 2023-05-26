package ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card


interface ArtistLocalStorage {

    fun getArtistCards(artistName: String): Collection<Card>
    fun saveArtist(card: Card, artistName: String) //TODO duda de RepositoryImpl
}