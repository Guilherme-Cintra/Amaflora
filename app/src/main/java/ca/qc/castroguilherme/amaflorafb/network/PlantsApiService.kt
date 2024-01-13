package ca.qc.castroguilherme.amaflorafb.network

import ca.qc.castroguilherme.amaflorafb.models.plantdbmodel.AllPlantsReponse
import ca.qc.castroguilherme.amaflorafb.models.plantdbmodel.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "sk-5OIY6597141b106743656"

interface PlantsApiService {

    @GET("/api/species-list")
    suspend fun getAll(
        @Query("key")
        apiKey: String = API_KEY
    ):Response<AllPlantsReponse>

    @GET("/api/species-list")
    suspend fun getResearch(
        @Query("q")
        searchQuerry:String,
        @Query("key")
        apiKey: String = API_KEY
    ):Response<AllPlantsReponse>

    @GET("/api/species/details/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("key")
        apiKey: String = API_KEY
    ):Response<DetailResponse>
}