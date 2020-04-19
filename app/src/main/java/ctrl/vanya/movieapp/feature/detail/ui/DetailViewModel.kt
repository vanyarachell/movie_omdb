package ctrl.vanya.movieapp.feature.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ctrl.vanya.movieapp.core.base.BaseViewState
import ctrl.vanya.movieapp.core.model.MovieDetailMdl
import ctrl.vanya.movieapp.core.utils.AppDispatchers
import ctrl.vanya.movieapp.data.repository.DetailRepository
import ctrl.vanya.movieapp.data.utils.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    private val _mMovieDetailResult = MutableLiveData<BaseViewState<MovieDetailMdl>>()

    val movieDetailResult: LiveData<BaseViewState<MovieDetailMdl>> = _mMovieDetailResult

    private var jobCallApi: Job? = null

    var mForceRefresh = false

    private var mPage = 1

    override fun onCleared() {
        super.onCleared()
        jobCallApi?.cancel()
    }

    fun getMovieDetail(imdbID: String) {
        _mMovieDetailResult.value = BaseViewState.Loading
        jobCallApi?.cancel()
        jobCallApi = viewModelScope.launch {
            val request = withContext(dispatchers.io) {
                repository.getMovieDetail(imdbID)
            }
            when (request) {
                is ResultState.Success -> {
                    _mMovieDetailResult.value = BaseViewState.Success(request.data)
                }
                is ResultState.Error -> {
                    _mMovieDetailResult.value = BaseViewState.Error(request.errorMessage)
                }
            }
        }
    }
}