package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Context
import ayds.lisboa3.submodule.lastFm.LastFmInjector
import ayds.ny3.newyorktimes.external.NYTimesArtistInfoServiceInjector.newYorkTimesArtistInfoServiceImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.CardsRepositoryImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.CardBrokerImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.LastFMProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.NewYorkTimesProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalServices.proxys.WikipediaProxy
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.ArtistCardStorageImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.CardDescriptionFormatterHtml
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoreDetailsView
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector.wikipediaService


object MoreDetailsInjector {

    private val cursorArtistMapper:CursorToArtistMapper = CursorToArtistMapperImpl()
    private val artistDescriptionFormatter = CardDescriptionFormatterHtml()

    fun init(moreDetailsView: MoreDetailsView){
        val brokerService = CardBrokerImpl()
        brokerService.addProxy(WikipediaProxy(wikipediaService))
        brokerService.addProxy(LastFMProxy(LastFmInjector.getService()))
        brokerService.addProxy(NewYorkTimesProxy(newYorkTimesArtistInfoServiceImpl))

        val artistLocalStorage = ArtistCardStorageImpl(moreDetailsView as Context, cursorArtistMapper)
        val repository = CardsRepositoryImpl(artistLocalStorage, brokerService)
        val presenter = MoreDetailsPresenterImpl(repository, artistDescriptionFormatter)
        moreDetailsView.setPresenter(presenter)
    }

}