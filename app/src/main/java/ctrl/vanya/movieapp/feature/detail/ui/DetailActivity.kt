package ctrl.vanya.movieapp.feature.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import ctrl.vanya.movieapp.R
import ctrl.vanya.movieapp.core.base.BaseViewState
import ctrl.vanya.movieapp.core.di.CoreModule
import ctrl.vanya.movieapp.feature.detail.di.DaggerDetailComponent
import ctrl.vanya.movieapp.feature.detail.di.DetailModule
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.progress_dialog.*
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMDB = "extra_imdb"
    }

    private var imdbID: String? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        injectDI()
        initObserve()
        setupUI()

        mViewModel.getMovieDetail(imdbID!!)
    }

    private fun injectDI() {
        DaggerDetailComponent.builder().detailModule(DetailModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        mViewModel.apply {
            movieDetailResult.observe(this@DetailActivity, Observer {
                when (it) {
                    is BaseViewState.Loading -> {}
                    is BaseViewState.Success -> {
                        progress_dialog.visibility = View.GONE
                        Glide.with(this@DetailActivity)
                            .load(it.data!!.poster)
                            .into(iv_movie_detail)

                        tv_movie_detail_title.text = it.data.title
                        tv_movie_detail_genre.text = it.data.genre
                        tv_movie_detail_duration.text = it.data.runtime
                        tv_movie_detail_year.text = it.data.year
                        tv_movie_detail_rate.text = it.data.ratings!![0].value
                        tv_movie_detail_description.text = it.data.plot
                    }
                    is BaseViewState.Error -> {
                        Log.e("error", it.errorMessage)
                    }
                }
            })
        }
    }

    private fun setupUI() {
        setToolbar(getString(R.string.detil_film))
        progress_dialog.visibility = View.VISIBLE
        imdbID = intent.extras?.getString(EXTRA_IMDB)
    }

    private fun setToolbar(title: String) {
        val toolbar = supportActionBar
        if (toolbar != null) {
            toolbar.title = title
            toolbar.elevation = 0f
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        } else {
            Log.e("error","Toolbar is not set")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
