package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("m")
    val m: String,
    @SerializedName("o")
    val o: String,
    @SerializedName("s")
    val s: String
)