package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Context
import ayds.winchester.songinfo.moredetails.fulllogic.data.RepositoryImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service.*
import ayds.winchester.songinfo.moredetails.fulllogic.data.externalWikipedia.service.WikipediaToArtistsResolverImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb.CursorToArtistMapper
import ayds.winchester.songinfo.moredetails.fulllogic.data.localWikipedia.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.ArtistDescriptionFormatterHtml
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.PresenterImpl
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.view.MoredetailsView
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object MoredetailsInjector {

    private val cursorArtistMapper:CursorToArtistMapper = CursorToArtistMapperImpl()
    private const val BASE_URL_RETROFIT = "https://en.wikipedia.org/w/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_RETROFIT)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
    private val wikipediaToSongResolver: WikipediaToArtistsResolver = WikipediaToArtistsResolverImpl()
    private val wikipediaService : WikipediaService = WikipediaServiceImpl(wikipediaToSongResolver, wikipediaAPI)
    private val artistDescriptionFormatter = ArtistDescriptionFormatterHtml()

    fun init(moreDetailsView: MoredetailsView){
        val artistLocalStorage = ArtistLocalStorageImpl(moreDetailsView as Context, cursorArtistMapper)
        val repository = RepositoryImpl(artistLocalStorage, wikipediaService)
        val presenter = PresenterImpl(repository, artistDescriptionFormatter)
        moreDetailsView.setPresenter(presenter)
    }
}