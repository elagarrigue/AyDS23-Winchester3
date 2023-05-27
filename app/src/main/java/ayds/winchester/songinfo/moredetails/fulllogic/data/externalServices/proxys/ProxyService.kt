package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface ProxyService {
    fun getArtistInfo(artistName:String): Card?
}
