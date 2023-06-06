package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices

import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.ProxyService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist

interface CardBroker{
    fun addProxy(proxyService: ProxyService)
    fun getArtistFromExternalServices(artistName: String): List<CardArtist>
}

internal class CardBrokerImpl:CardBroker{

    private var proxyServices : MutableCollection<ProxyService> = mutableListOf()

    override fun addProxy(proxyService: ProxyService){
        proxyServices.add(proxyService)
    }

    override fun getArtistFromExternalServices(artistName: String): List<CardArtist> {
        val collectionCardArtists = mutableListOf<CardArtist>()
        proxyServices.forEach {
            it.getArtistInfo(artistName)?.let {
                    card -> collectionCardArtists.add(card)
            }
        }
        return collectionCardArtists
    }
}