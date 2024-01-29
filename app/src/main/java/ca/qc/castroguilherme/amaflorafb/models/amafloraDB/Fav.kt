package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class Fav(
    @SerializedName("id")
    val id: Int,
    @SerializedName("plantId")
    val plantId: Int,
    @SerializedName("userId")
    val userId: String
)