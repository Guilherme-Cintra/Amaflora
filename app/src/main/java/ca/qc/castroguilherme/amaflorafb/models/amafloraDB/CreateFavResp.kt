package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class CreateFavResp(
    @SerializedName("fav")
    val fav: Fav,
    @SerializedName("message")
    val message: String
)