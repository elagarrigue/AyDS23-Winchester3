package ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys

import ayds.ny3.newyorktimes.external.NYTArtistInfo
import ayds.ny3.newyorktimes.external.NYTimesArtistInfoService
import ayds.winchester.songinfo.moredetails.fulllogic.domain.entities.Card

private const val NYTIMES_LOGO_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"

class NewYorkTimesProxy(
    private val newYorkTimesService: NYTimesArtistInfoService
): ProxyService {

    override fun getArtistInfo(artistName: String):Card?=
        newYorkTimesService.getArtistInfo(artistName)?.mapNYTimesArtist()

    private fun NYTArtistInfo?.mapNYTimesArtist():Card?=
        this?.let {
            Card(
                "NewYorkTimes",
                this.url,
                NYTIMES_LOGO_URL,
                this.abstract
            )
        }
}