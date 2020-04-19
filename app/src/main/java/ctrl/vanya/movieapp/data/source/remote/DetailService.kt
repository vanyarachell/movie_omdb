package ctrl.vanya.movieapp.data.source.remote

import ctrl.vanya.movieapp.core.model.MovieDetailMdl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailService {
    @GET(".")
    suspend fun getMovieDetail(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String?
    ): Response<MovieDetailMdl>
}