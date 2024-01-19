package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("commonNames")
    val commonNames: List<String>,
    @SerializedName("family")
    val family: Family,
    @SerializedName("genus")
    val genus: Genus,
    @SerializedName("scientificName")
    val scientificName: String,
    @SerializedName("scientificNameAuthorship")
    val scientificNameAuthorship: String,
    @SerializedName("scientificNameWithoutAuthor")
    val scientificNameWithoutAuthor: String
)