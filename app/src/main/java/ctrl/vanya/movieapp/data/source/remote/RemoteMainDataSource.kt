package ctrl.vanya.movieapp.data.source.remote

import ctrl.vanya.movieapp.core.model.MovieListMdl
import ctrl.vanya.movieapp.data.source.MainDataSource
import ctrl.vanya.movieapp.data.utils.ResultState
import ctrl.vanya.movieapp.data.utils.fetchState
import ctrl.vanya.movieapp.data.utils.handleError
import javax.inject.Inject

class RemoteMainDataSource @Inject constructor(
    private val mainService: MainService
) : MainDataSource {
    override suspend fun getMovieList(keyword : String, page : Int): ResultState<MovieListMdl> {
        return fetchState {
            val response = mainService.getMovieList("7ecb94ad", keyword, "movie", page)
            val data: MovieListMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }
}