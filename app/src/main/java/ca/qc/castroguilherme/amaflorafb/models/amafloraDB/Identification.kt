package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class Identification(
    @SerializedName("dateDidentification")
    val dateDidentification: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageDidentification")
    val imageDidentification: String,
    @SerializedName("nomScientifique")
    val nomScientifique: String,
    @SerializedName("userId")
    val userId: String
)