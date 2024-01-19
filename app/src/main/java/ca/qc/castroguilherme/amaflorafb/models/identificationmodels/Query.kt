package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("includeRelatedImages")
    val includeRelatedImages: Boolean,
    @SerializedName("organs")
    val organs: List<String>,
    @SerializedName("project")
    val project: String
)