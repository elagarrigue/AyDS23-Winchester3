package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.lisboa3.submodule.lastFm.LAST_FM_DEFAULT_IMAGE
import ayds.lisboa3.submodule.lastFm.LastFmArtistInfo
import ayds.lisboa3.submodule.lastFm.LastFmService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source

class LastFMProxy(
    private val lastFmService: LastFmService
): ProxyService {

    override fun getArtistInfo(artistName: String):Card?=
        lastFmService.getArtistInfo(artistName)?.mapLastFmArtis()

    private fun LastFmArtistInfo?.mapLastFmArtis():Card?=
        this?.let{
            Card(
                Source.LAST_FM,
                this.url,
                LAST_FM_DEFAULT_IMAGE,
                this.bioContent
            )
        }

}