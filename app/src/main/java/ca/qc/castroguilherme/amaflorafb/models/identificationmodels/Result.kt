package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("gbif")
    val gbif: Gbif,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("iucn")
    val iucn: Iucn,
    @SerializedName("powo")
    val powo: Powo,
    @SerializedName("score")
    val score: Double,
    @SerializedName("species")
    val species: Species
)