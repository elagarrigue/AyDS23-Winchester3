package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Context
import ayds.winchester.songinfo.moredetails.fulllogic.data.RepositoryImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localArtistInfo.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.ArtistDescriptionFormatterHtml
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.PresenterImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsView
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector


object MoredetailsInjector {

    private val cursorArtistMapper:CursorToArtistMapper = CursorToArtistMapperImpl()
    private val artistDescriptionFormatter = ArtistDescriptionFormatterHtml()

    fun init(moreDetailsView: MoredetailsView){
        val artistLocalStorage = ArtistLocalStorageImpl(moreDetailsView as Context, cursorArtistMapper)
        val wikipediaService = WikipediaInjector.wikipediaService
        val repository = RepositoryImpl(artistLocalStorage, wikipediaService)
        val presenter = PresenterImpl(repository, artistDescriptionFormatter)
        moreDetailsView.setPresenter(presenter)
    }

    //TODO cambiar para que el repository reciba un broker
}