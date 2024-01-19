package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("author")
    val author: String,
    @SerializedName("citation")
    val citation: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("license")
    val license: String,
    @SerializedName("organ")
    val organ: String,
    @SerializedName("url")
    val url: Url
)