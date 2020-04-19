package ctrl.vanya.movieapp.data.repository

import ctrl.vanya.movieapp.core.model.MovieListMdl
import ctrl.vanya.movieapp.data.source.MainDataSource
import ctrl.vanya.movieapp.data.utils.ResultState
import javax.inject.Inject

interface MainRepository {
    suspend fun getMovieList(keyword : String, page : Int): ResultState<MovieListMdl>

    class MainRepositoryImpl @Inject constructor(private val remoteMainDataSource: MainDataSource) : MainRepository {
        override suspend fun getMovieList(keyword : String, page : Int): ResultState<MovieListMdl> {
            return remoteMainDataSource.getMovieList(keyword, page)
        }

    }
}