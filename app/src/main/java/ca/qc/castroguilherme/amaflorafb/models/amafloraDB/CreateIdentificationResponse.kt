package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class CreateIdentificationResponse(
    @SerializedName("identification")
    val identification: IdentificationXX,
    @SerializedName("message")
    val message: String
)