package ca.qc.castroguilherme.amaflora.models.identificationmodels




import com.google.gson.annotations.SerializedName

data class IdentificationResponse(
    @SerializedName("bestMatch")
    val bestMatch: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("preferedReferential")
    val preferedReferential: String,
    @SerializedName("query")
    val query: Query,
    @SerializedName("remainingIdentificationRequests")
    val remainingIdentificationRequests: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("switchToProject")
    val switchToProject: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("statusCode")
    val statusCode: Long?,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String?
)