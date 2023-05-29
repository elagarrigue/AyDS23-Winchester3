package ayds.winchester.songinfo.moredetails.fulllogic.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ayds.observer.Observer
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.fulllogic.MoredetailsInjector
import ayds.winchester.songinfo.moredetails.fulllogic.presentation.presenter.Presenter
import me.relex.circleindicator.CircleIndicator3

interface MoredetailsView {
    fun setPresenter(presenter: Presenter)


}

class MoredetailsActivity:MoredetailsView, AppCompatActivity() {
    private lateinit var presenter: Presenter
    private lateinit var viewPager: ViewPager2
    private lateinit var circleIndicator: CircleIndicator3
    private val observer: Observer<MoredetailsUIState> =
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

    private fun updateUIComponents(uiState: MoredetailsUIState){
        val cards = uiState.cards
        viewPager.adapter = ArtistViewPagerAdapter(cards, this)
        circleIndicator.setViewPager(viewPager)
    }

    override fun setPresenter(presenter: Presenter) {
        this.presenter = presenter
    }

    private fun initModule(){
        MoredetailsInjector.init(this)
    }


    private fun initObservers() {
        presenter.uiStateObservable.subscribe(observer)
    }

    private fun getArtistNameFromIntent() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()

    private fun openArtistInfoWindow(artistName:String) {
        presenter.showArtistInfo(artistName)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}