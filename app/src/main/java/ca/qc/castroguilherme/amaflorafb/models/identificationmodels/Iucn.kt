package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Iucn(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: String
)