package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.ny3.newyorktimes.external.NYTArtistInfo
import ayds.ny3.newyorktimes.external.NYT_LOGO_URL
import ayds.ny3.newyorktimes.external.NYTimesArtistInfoService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Source

class NewYorkTimesProxy(
    private val newYorkTimesService: NYTimesArtistInfoService
): ProxyService {

    override fun getArtistInfo(artistName: String):Card?=
        newYorkTimesService.getArtistInfo(artistName)?.mapNYTimesArtist()

    private fun NYTArtistInfo?.mapNYTimesArtist():Card?=
        this?.let {
            Card(
                Source.NEW_YORK_TIMES,
                this.url,
                NYT_LOGO_URL,
                this.abstract
            )
        }
}