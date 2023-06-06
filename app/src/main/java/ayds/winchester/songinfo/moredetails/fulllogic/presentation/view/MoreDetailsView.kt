package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ayds.observer.Observer
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.fulllogic.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.MoreDetailsPresenter
import me.relex.circleindicator.CircleIndicator3

interface MoreDetailsView {
    fun setPresenter(moreDetailsPresenter: MoreDetailsPresenter)

}

class MoreDetailsActivity:MoreDetailsView, AppCompatActivity() {
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var viewPager: ViewPager2
    private lateinit var circleIndicator: CircleIndicator3
    private val observer: Observer<MoreDetailsUIState> =
        Observer { state ->
            updateUIComponents(state)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initObservers()
        initWindow()
    }

    private fun initProperties(){
        viewPager = findViewById(R.id.viewPager)
        circleIndicator = findViewById(R.id.circleIndicator)
    }

    private fun initWindow() {
        val artistName = getArtistNameFromIntent()
        openArtistInfoWindow(artistName)
    }

    private fun updateUIComponents(uiState: MoreDetailsUIState){
        runOnUiThread {
            viewPager.adapter = ArtistViewPagerAdapter(uiState.cards, this)
            circleIndicator.setViewPager(viewPager)
        }

    }

    override fun setPresenter(moreDetailsPresenter: MoreDetailsPresenter) {
        this.moreDetailsPresenter = moreDetailsPresenter
    }

    private fun initModule(){
        MoreDetailsInjector.init(this)
    }


    private fun initObservers() {
        moreDetailsPresenter.uiStateObservable.subscribe(observer)
    }

    private fun getArtistNameFromIntent() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()

    private fun openArtistInfoWindow(artistName:String) {
        moreDetailsPresenter.showArtistInfo(artistName)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}