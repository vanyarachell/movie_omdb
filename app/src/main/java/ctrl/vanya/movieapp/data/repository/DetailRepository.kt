package ctrl.vanya.movieapp.data.repository

import ctrl.vanya.movieapp.core.model.MovieDetailMdl
import ctrl.vanya.movieapp.data.source.DetailDataSource
import ctrl.vanya.movieapp.data.utils.ResultState
import javax.inject.Inject

interface DetailRepository {
    suspend fun getMovieDetail(imdbID: String): ResultState<MovieDetailMdl>

    class DetailRepositoryImpl @Inject constructor(private val remoteDetailDataSource: DetailDataSource) : DetailRepository {
        override suspend fun getMovieDetail(imdbID: String): ResultState<MovieDetailMdl> {
            return remoteDetailDataSource.getMovieDetail(imdbID)
        }

    }
}