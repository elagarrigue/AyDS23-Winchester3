package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.ProxyService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

interface Broker{
    fun addProxy(proxyService: ProxyService)
    fun getArtistFromExternalServices(artistName: String):Collection<Card>
}

internal class BrokerImpl:Broker{

    private var proxyServices : MutableCollection<ProxyService> = mutableListOf()

    override fun addProxy(proxyService: ProxyService){
        proxyServices.add(proxyService)
    }

    override fun getArtistFromExternalServices(artistName: String): Collection<Card>{
        var collectionCards = mutableListOf<Card>()
        proxyServices.forEach {
            it.getArtistInfo(artistName)?.let {
                    card -> collectionCards.add(card)
            }
        }
        return collectionCards
    }
}