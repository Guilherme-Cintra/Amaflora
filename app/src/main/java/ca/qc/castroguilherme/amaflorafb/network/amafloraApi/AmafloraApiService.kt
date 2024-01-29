package ca.qc.castroguilherme.amaflorafb.network.amafloraApi

import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreateFavResp
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreateIdentification
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreateIdentificationResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.DeleteIdentificationResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.DeleteOwnedResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.FavBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.FavDeleteResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetIdentificationListResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetPlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheSauvegardeResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UserResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.HistoriqueResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlants
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.WishListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("/api/wishlist/{uid}")
    suspend fun getWishList(
        @Path("uid") uid: String
    ): Response<WishListResponse>

    @GET("/api/identifications/{uid}")
    suspend fun getIdentifications(
        @Path("uid") uid: String
    ): Response<GetIdentificationListResponse>


    @POST("/api/identifications/")
    suspend fun postIdentification(
        @Body createIdentification: CreateIdentification
    ):Response<CreateIdentificationResponse>

    @DELETE("/api/identifications/delete/{id}")
    suspend fun deleteIdentification(
        @Path("id") id: Int
    ):Response<DeleteIdentificationResponse>

    @POST("/api/wishlist/")
    suspend fun addToFav(
        @Body favBody: FavBody
    ):Response<CreateFavResp>

    @POST("/api/userplants/create/")
    suspend fun addOwnedPlant(
        @Body ownedPlantBody: OwnedPlantBody
    ):Response<OwnedPlantResponse>

    @DELETE("/api/userplants/delete/{id}")
    suspend fun deleteOwnedPlant(
        @Path("id") id: Int
    ):Response<DeleteOwnedResponse>

    @PUT("/api/userplants/update/{id}")
    suspend fun updatePlant(
        @Path("id") id: Int,
        @Body updatePlantBody: UpdatePlantBody
    ):Response<UpdatePlantResponse>

    @DELETE("/api/wishlist/delete/{id}")
    suspend fun deleteFavourite(
        @Path("id") id: Int
    ): Response<FavDeleteResponse>

}