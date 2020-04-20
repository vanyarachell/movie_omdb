package ctrl.vanya.movieapp.feature.list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ctrl.vanya.movieapp.core.base.BaseViewState
import ctrl.vanya.movieapp.core.model.MovieListMdl
import ctrl.vanya.movieapp.core.utils.AppDispatchers
import ctrl.vanya.movieapp.data.repository.MainRepository
import ctrl.vanya.movieapp.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _mMovieListResult = MutableLiveData<BaseViewState<MovieListMdl>>()

    val movieListResult: LiveData<BaseViewState<MovieListMdl>> = _mMovieListResult

    private var jobCallApi: Job? = null

    var mForceRefresh = false

    private var mPage = 1

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getMovieList(keyword : String, page : Int) {
        mPage = page
        _mMovieListResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieList(keyword, page)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieListResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieListResult.value = BaseViewState.Error(request.errorMessage)
                }
            }
        }
    }

    fun forceLoadMovie(keyword : String, page : Int) {
        mForceRefresh = true
        _mMovieListResult.value = null
        getMovieList(keyword, page)
    }
}