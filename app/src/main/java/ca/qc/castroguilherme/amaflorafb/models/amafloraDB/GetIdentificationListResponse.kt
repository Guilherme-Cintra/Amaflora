package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class GetIdentificationListResponse(
    @SerializedName("identifications")
    val identifications: List<Identification>,
    @SerializedName("message")
    val message: String
)