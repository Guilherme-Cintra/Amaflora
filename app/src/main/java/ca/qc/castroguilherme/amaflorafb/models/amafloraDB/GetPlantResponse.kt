package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class GetPlantResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("plant")
    val plant: Plant
)