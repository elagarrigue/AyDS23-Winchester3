package ayds.winchester.songinfo.home.view

import ayds.winchester.songinfo.home.controller.HomeControllerInjector
import ayds.winchester.songinfo.home.model.HomeModelInjector
import ayds.winchester.songinfo.home.view.formatter.PrecisionFormatterFactoryImpl

object HomeViewInjector {

    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(PrecisionFormatterFactoryImpl)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}