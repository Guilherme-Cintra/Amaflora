package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class CreatePlantResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("newPlant")
    val newPlant: NewPlant?
)