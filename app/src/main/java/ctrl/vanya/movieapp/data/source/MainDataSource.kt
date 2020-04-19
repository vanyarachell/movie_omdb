package ctrl.vanya.movieapp.data.source

import ctrl.vanya.movieapp.core.model.MovieListMdl
import ctrl.vanya.movieapp.data.utils.ResultState

interface MainDataSource {
    suspend fun getMovieList(keyword : String, page : Int): ResultState<MovieListMdl>
}