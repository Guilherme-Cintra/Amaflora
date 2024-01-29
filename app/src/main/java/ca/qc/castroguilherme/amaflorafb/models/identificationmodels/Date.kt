package ca.qc.castroguilherme.amaflora.models.identificationmodels



import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("string")
    val string: String,
//    @SerializedName("timestamp")
//    val timestamp: Int
)