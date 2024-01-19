package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class GetPlantBody(
    @SerializedName("id")
    val id: Int
)