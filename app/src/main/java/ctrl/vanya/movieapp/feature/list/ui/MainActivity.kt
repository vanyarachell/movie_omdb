@file:Suppress("DEPRECATION")

package ctrl.vanya.movieapp.feature.list.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ctrl.vanya.movieapp.R
import ctrl.vanya.movieapp.core.base.BaseViewState
import ctrl.vanya.movieapp.core.di.CoreModule
import ctrl.vanya.movieapp.core.model.MovieMdl
import ctrl.vanya.movieapp.core.utils.OnLoadMoreListener
import ctrl.vanya.movieapp.feature.list.di.DaggerMainComponent
import ctrl.vanya.movieapp.feature.list.di.MainModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.no_internet_layout.*
import kotlinx.android.synthetic.main.progress_dialog.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MainAdapter
    private var list: ArrayList<MovieMdl>? = null
    private var currentPage = 1
    private var totalPage: Int = 4
    private var keyword = "love"
    private var newData = true

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDI()
        initObserve()
        initRecyclerView()
        initViewAction()
        setupUI()
    }

    private fun setupUI(){
        list = ArrayList()
        mViewModel.getMovieList(keyword, currentPage)

        //setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.daftar_film)
        setSupportActionBar(toolbar)
    }

    private fun injectDI() {
        DaggerMainComponent.builder().mainModule(MainModule())
            .coreModule(CoreModule(this))
            .build().inject(this)
    }

    private fun initObserve() {
        mViewModel.apply {
            movieListResult.observe(this@MainActivity, Observer {
                when (it) {
                    is BaseViewState.Loading -> {
                        if (mForceRefresh && !mAdapter.isLoading) {
                            showLoading()
                        }
                    }
                    is BaseViewState.Success -> {
                        stopLoading()

                        if (newData){
                            list = ArrayList()
                            newData = false
                        }
                        list!!.addAll(it.data!!.search!!)
                        mAdapter.addDataAndSubmit(list!!)

                        mAdapter.isLoading = false
                    }
                    is BaseViewState.Error -> {
                        stopLoading()
                        if (it.errorMessage!!.contains("connection")){
                            mAdapter.isLoading = false
                            initNoConnection()
                        }
                    }
                }
            })
        }
    }

    private fun showLoading() {
        progress_dialog.isVisible = true
    }

    private fun stopLoading() {
        progress_dialog.isVisible = false
    }

    private fun initNoConnection() {
        progress_dialog.isVisible = false
        rvMovieList.isVisible = false
        rl_no_internet.isVisible = true
    }

    private fun initRecyclerView() {
        mAdapter = MainAdapter(this)
        mAdapter.setLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                rvMovieList.post {
                    currentPage++
                    if (currentPage > totalPage) {
                        mAdapter.setMoreDataAvailable(false)
                    } else {
                        progress_dialog.visibility = View.VISIBLE
                        mViewModel.getMovieList(keyword, currentPage)
                    }
                }
            }
        })
        rvMovieList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initViewAction() {
        btn_retry.setOnClickListener {
            mViewModel.forceLoadMovie("", 1)
            rl_no_internet.isVisible = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //search manager
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //reset list on adapter
                mViewModel.getMovieList(query, 1)
                //hide keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow((currentFocus)!!.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener { false }
        return true
    }

}
