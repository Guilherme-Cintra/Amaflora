package ca.qc.castroguilherme.amaflorafb.network.amafloraApi

import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetPlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheSauvegardeResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UserResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.HistoriqueResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface AmafloraApiService {
    @POST("/api/users/login/")
    suspend fun saveUser(
        @Body user: User
    ): Response<UserResponse>

    @POST("/api/plants/create")
    suspend fun createPlant(
        @Body createPlant: CreatePlant
    ): Response<CreatePlantResponse>

    @GET("/api/plants/getPlant/{id}")
    suspend fun getPlantById(
        @Path("id") id: Int
    ): Response<GetPlantResponse>

    @POST("/api/recherche/create/")
    suspend fun sauvegarderRecherche(
        @Body rechercheBody: RechercheBody
    ): Response<RechercheSauvegardeResponse>

    @GET("/api/recherche/historique/{uid}")
    suspend fun getHistorique(
        @Path("uid") uid: String
    ): Response<HistoriqueResponse>

    @GET("/api/userplants/list/{uid}")
    suspend fun getUserPlants(
        @Path("uid") uid: String
    ): Response<ListPlants>


}