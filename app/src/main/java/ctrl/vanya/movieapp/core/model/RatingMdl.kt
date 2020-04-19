package ctrl.vanya.movieapp.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class RatingMdl {
    @SerializedName("Source")
    @Expose
    var source: String? = null
    @SerializedName("Value")
    @Expose
    var value: String? = null
}