package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Context
import ayds.lisboa3.submodule.lastFm.LastFmInjector
import ayds.ny3.newyorktimes.external.NYTimesArtistInfoServiceInjector.newYorkTimesArtistInfoServiceImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.RepositoryImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.BrokerImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.LastFMProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.NewYorkTimesProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.WikipediaProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.ArtistDescriptionFormatterHtml
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.PresenterImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsView
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector.wikipediaService



object MoredetailsInjector {

    private val cursorArtistMapper:CursorToArtistMapper = CursorToArtistMapperImpl()
    private val artistDescriptionFormatter = ArtistDescriptionFormatterHtml()
    private val brokerService = BrokerImpl()


    fun init(moreDetailsView: MoredetailsView){
        brokerService.addProxy(WikipediaProxy(wikipediaService))
        brokerService.addProxy(LastFMProxy(LastFmInjector.getService()))
        brokerService.addProxy(NewYorkTimesProxy(newYorkTimesArtistInfoServiceImpl))

        val artistLocalStorage = ArtistLocalStorageImpl(moreDetailsView as Context, cursorArtistMapper)
        val repository = RepositoryImpl(artistLocalStorage, brokerService)
        val presenter = PresenterImpl(repository, artistDescriptionFormatter)
        moreDetailsView.setPresenter(presenter)
    }

    //TODO cambiar para que el repository reciba un broker
}