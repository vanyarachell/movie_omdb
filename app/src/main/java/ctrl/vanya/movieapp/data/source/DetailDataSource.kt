package ctrl.vanya.movieapp.data.source

import ctrl.vanya.movieapp.core.model.MovieDetailMdl
import ctrl.vanya.movieapp.data.utils.ResultState

interface DetailDataSource {
    suspend fun getMovieDetail(imdbID: String): ResultState<MovieDetailMdl>
}