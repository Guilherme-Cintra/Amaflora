package ca.qc.castroguilherme.amaflorafb.network.plantidentification


import ca.qc.castroguilherme.amaflora.models.identificationmodels.IdentificationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "2b1032IRdwIlPoPFQFbNMzMg2e"
interface PlantIdentificationApiService {
    @Multipart
    @POST("/v2/identify/{project}")
    suspend fun identify(
        @Path("project") project: String= "all",
        @Part images: List<MultipartBody.Part>,
        @Query("api-key") apiKey: String = API_KEY,
    ): Response<IdentificationResponse>
}