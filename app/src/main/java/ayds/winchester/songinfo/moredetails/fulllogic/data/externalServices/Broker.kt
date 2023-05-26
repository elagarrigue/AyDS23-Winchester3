package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices

import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface Broker{
    fun addProxy(proxyService: ProxyService)
    fun getArtistFromExternalServices(artistName: String):Collection<Card>
}

//TODO brokerImpl