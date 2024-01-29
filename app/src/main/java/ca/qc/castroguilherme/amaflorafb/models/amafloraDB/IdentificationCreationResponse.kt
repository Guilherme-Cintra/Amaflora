package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class IdentificationCreationResponse(
    @SerializedName("identification")
    val identification: Identification,
    @SerializedName("message")
    val message: String
)