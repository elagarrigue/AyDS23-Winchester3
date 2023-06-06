package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

interface ProxyService {
    fun getCard(artistName:String): CardArtist?
}
