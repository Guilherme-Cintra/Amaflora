package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class CreateIdentification(
    @SerializedName("imageDidentification")
    val imageDidentification: String?,
    @SerializedName("nomScientifique")
    val nomScientifique: String,
    @SerializedName("userId")
    val userId: String
)