package ctrl.vanya.movieapp.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class MovieListMdl {
    @SerializedName("Search")
    @Expose
    var search: List<MovieMdl>? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null
    @SerializedName("Response")
    @Expose
    var response: String? = null
}