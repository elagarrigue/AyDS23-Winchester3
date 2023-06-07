package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.lisboa3.submodule.lastFm.LAST_FM_DEFAULT_IMAGE
import ayds.lisboa3.submodule.lastFm.LastFmArtistInfo
import ayds.lisboa3.submodule.lastFm.LastFmService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.CardArtist
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source
import java.io.IOException

class LastFMProxy(
    private val lastFmService: LastFmService
): ProxyService {

    override fun getCard(artistName: String):CardArtist?=
        try {
            lastFmService.getArtistInfo(artistName)?.mapLastFmArtis()
        } catch (e1: IOException) {
            null
        }
    private fun LastFmArtistInfo?.mapLastFmArtis():CardArtist?=
        this?.let{
            CardArtist(
                Source.LAST_FM,
                this.url,
                LAST_FM_DEFAULT_IMAGE,
                this.bioContent
            )
        }
}