package ctrl.vanya.movieapp.data.source.remote

import ctrl.vanya.movieapp.core.model.MovieDetailMdl
import ctrl.vanya.movieapp.data.source.DetailDataSource
import ctrl.vanya.movieapp.data.utils.ResultState
import ctrl.vanya.movieapp.data.utils.fetchState
import ctrl.vanya.movieapp.data.utils.handleError
import javax.inject.Inject

class RemoteDetailDataSource @Inject constructor(
    private val detailService: DetailService
) : DetailDataSource {
    override suspend fun getMovieDetail(imdbID: String): ResultState<MovieDetailMdl> {
        return fetchState {
            val response = detailService.getMovieDetail("7ecb94ad", imdbID)
            val data: MovieDetailMdl
            if (response.isSuccessful) {
                data = response.body()!!
                ResultState.Success(data)
            } else {
                ResultState.Error(response.handleError().message)
            }
        }
    }
}