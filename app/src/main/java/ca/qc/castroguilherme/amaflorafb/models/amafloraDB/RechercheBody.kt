package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class RechercheBody(
    @SerializedName("date")
    val date: String,
    @SerializedName("plantId")
    val plantId: Int,
    @SerializedName("userId")
    val userId: String
)