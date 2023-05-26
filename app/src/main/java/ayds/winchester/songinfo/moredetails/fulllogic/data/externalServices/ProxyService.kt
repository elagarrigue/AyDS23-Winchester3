package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface ProxyService {
    fun getArtistInfo(artistName:String): Card?
}

//TODO implementar los proxys de los servicios