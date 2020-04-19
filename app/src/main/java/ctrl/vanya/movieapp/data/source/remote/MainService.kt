package ctrl.vanya.movieapp.data.source.remote

import ctrl.vanya.movieapp.core.model.MovieListMdl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET(".")
    suspend fun getMovieList(
        @Query("apikey") apiKey: String,
        @Query("s") keyword: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<MovieListMdl>
}