package ctrl.vanya.movieapp.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MovieMdl(
    @field:SerializedName("loading") @field:Expose var loading: String = "",
    @SerializedName("Title")
    @Expose
    var title: String? = null,
    @SerializedName("Year")
    @Expose
    var year: String? = null,
    @SerializedName("imdbID")
    @Expose
    var imdbID: String? = null,
    @SerializedName("Type")
    @Expose
    var type: String? = null,
    @SerializedName("Poster")
    @Expose
    var poster: String? = null
)