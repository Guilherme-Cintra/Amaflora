package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.HistoriqueResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlants
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheSauvegardeResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UserResponse
import ca.qc.castroguilherme.amaflorafb.models.identificationmodels.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch

class AmaFloraViewModel(private val amaFloraRepository: AmaFloraRepository) : ViewModel() {






    val userCreationResponse: MutableLiveData<UserResponse> = MutableLiveData()
    fun saveUser(user: User) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.saveUser(user)
            if (response.isSuccessful) {
                userCreationResponse.postValue(response.body())
                Log.i("MyApiName", "user name : ${response.body()?.displayName}")
            }
        } catch (e: Exception) {
            Log.i("MyApi", "saveUser caught an error ")
        }
    }


    val platCreationResponse: MutableLiveData<CreatePlantResponse> = MutableLiveData()
    fun createPlant(createPlant: CreatePlant, recherche: RechercheBody) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.createPlant(createPlant)
            if (response.isSuccessful) {

                save(recherche)
                Log.i("MyApi", "${response.body()?.message}")
            } else {
                Log.i("MyApi", "${response.body()?.message}")
            }
        } catch (e: Exception) {
            Log.i("MyApi", "create plant caught error ${e.message}")
        }
    }

    val getPlantResponse: MutableLiveData<GetPlantResponse> = MutableLiveData()



    fun sauvegarderRecherche(recherche: RechercheBody) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.rechercheSauvegarde(recherche)
            if (response.isSuccessful) {
                Log.i("MyApi", " success ${response.body()?.recherche}")
            } else {
                Log.i("MyApi", "erreur ${response.body()?.message}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error recherche saving: $errorBody")


                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e: Exception) {
            Log.i(
                "MyApi", "sauvegardeRecherche caught an error, " +
                        "${e.message}"
            )
        }
    }


    val rechercheSauvegardeResponse: MutableLiveData<RechercheSauvegardeResponse> = MutableLiveData()

    val historiqueResponse : MutableLiveData<HistoriqueResponse> = MutableLiveData()
    val plantsLiveData: MutableLiveData<List<Plant>?> = MutableLiveData()
    suspend fun getPlantById(id: Int): Plant? {
        return try {
            val response = amaFloraRepository.getPlantById(id)
            if (response.isSuccessful) {
                Log.i("MyApi", "id : ${response.body()?.plant?.id}")
                response.body()?.plant
            } else {
                null
            }
        }catch (e: Exception) {
            null
        }
    }

    suspend fun save(recherche: RechercheBody) {
         try {
            val response = amaFloraRepository.rechercheSauvegarde(recherche)
             if (response.isSuccessful){
                 Log.i("saving", "recherche sauvagerdée avec succès id plant: ${response.body()?.recherche?.plantId}")

             } else {
                 Log.i("saving", "recherche sauvagerdée failed: ${response.errorBody()}")

             }
        }catch (e: Exception) {
            Log.i("saving", "error saving ${e.message}")
        }
    }


    fun getHistorique(uid: String) = viewModelScope.launch {
        try {

            val response = amaFloraRepository.getHistorique(uid)
            if (response.isSuccessful){

                Log.i("MyApi", "historique success ${response.body()?.recherches?.size}")

                val recherches = response.body()?.recherches
                val plants = mutableListOf<Plant>()
                recherches?.forEach { recherche ->
                    getPlantById(recherche.plantId)?.let { plant ->
                        plants.add(plant)
                    }
                }


                val filteredPlants = plants.distinct().reversed()
                plantsLiveData.postValue(filteredPlants)

            } else {
                Log.i("MyApi", "erreur ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error get historique: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e: Exception) {
            Log.i("MyApi", "historique caught an error ${e.message}")
        }
    }

    val userPlantsReponse : MutableLiveData<ListPlants> = MutableLiveData()


    fun getUserPlants(uid: String)  = viewModelScope.launch {
        try {
            val response = amaFloraRepository.getListPlants(uid)
            if (response.isSuccessful){
                userPlantsReponse.postValue(response.body())
                Log.i("MyApi", "${response.body()?.size}")

            } else {
                Log.i("MyApi", "erreur ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error get user plants: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e: Exception) {
            Log.i("MyApi", "user's plants caught an error ${e.message}")
        }
    }

//    val getWishResponse : MutableLiveData<WishListResponse> = MutableLiveData()
    val wishListLiveData : MutableLiveData<List<Plant>?> = MutableLiveData()
    fun getWishList(uid: String) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.getWishList(uid)
            if (response.isSuccessful){

                val wishes = response.body()?.wishList
                val plants = mutableListOf<Plant>()
                wishes?.forEach {
                    getPlantById(it.plantId)?.let { plant ->
                        plants.add(plant)
                    }
                }

                val filteredPlants = plants.distinct()
                wishListLiveData.postValue(filteredPlants)
            } else {
                Log.i("MyApi", "erreur ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error get wish list: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        }catch (e: Exception) {
            Log.i("MyApi", "wish list caught an error ${e.message}")
        }
    }

    val listIdentifications : MutableLiveData<GetIdentificationListResponse> = MutableLiveData()
    fun getIdentificationList(uid: String) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.getIdentificationsList(uid)
            if (response.isSuccessful){
                listIdentifications.postValue(response.body())
                Log.i("MyApi", " success size identifications : ${response.body()?.identifications?.size}")
            } else {
                Log.i("MyApi", "erreur ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error identificaiotn list: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }

        } catch (e: Exception) {
            Log.i("MyApi", "getIdentification caught an error ${e.message}")
        }
    }

    val identificationResponse: MutableLiveData<CreateIdentificationResponse> = MutableLiveData()
    fun postIdentification(createIdentification: CreateIdentification)= viewModelScope.launch {
        try {
            val response = amaFloraRepository.postIdentification(createIdentification)
            if (response.isSuccessful){
                identificationResponse.postValue(response.body())
                Log.i("idenSuccess", "success saving ${response.body()?.identification?.imageDidentification}")
            }  else {
                Log.i("idenSuccess", "erreur create identification ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("idenSuccess", "Error create identification: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e:Exception){
            Log.i("idenSuccess", "createIdentification caught an error ${e.message}")

        }
    }

    val favResponse: MutableLiveData<CreateFavResp> = MutableLiveData()
    fun addToFav(favBody: FavBody) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.postFavourite(favBody)
            if (response.isSuccessful){
                favResponse.postValue(response.body())
                Log.i("Fav", "fav success : ${response.body()?.message}")
            }  else {
                Log.i("MyApi", "erreur create creating fav ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error create favourite: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e:Exception){
            Log.i("MyApi", "add fav caught an error ${e.message}")
        }
    }

    val ownedPlantResponse: MutableLiveData<OwnedPlantResponse> = MutableLiveData()

    fun addOwnedPlant(ownedPlantBody: OwnedPlantBody) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.addOwnedPlant(ownedPlantBody)
            if (response.isSuccessful){
                ownedPlantResponse.postValue(response.body())
                Log.i("Owned", " new plant id: ${response.body()?.id}")
            } else  {
                Log.i("MyApi", "erreur adding plant ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error create identification: $errorBody")
            }
        } catch (e:Exception){
            Log.i("MyApi", "add owned caught an error ${e.message}")
        }
    }

    val deleteResponse: MutableLiveData<DeleteOwnedResponse> = MutableLiveData()
    fun deleteOwned(id: Int) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.deleteOwnedPlant(id)
            if (response.isSuccessful){
                deleteResponse.postValue(response.body())
                 Log.i("Delete", "message : ${response.body()?.message}")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.i("Delete", "Erreur deleting ${errorBody}")
            }
        } catch (e:Exception){
            Log.i("MyApi", "add owned caught an error ${e.message}")
        }
    }

    val updateReponse : MutableLiveData<UpdatePlantResponse> = MutableLiveData()
    fun updatePlant(id: Int, updatePlantBody: UpdatePlantBody) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.updatePlant(id, updatePlantBody)
            if (response.isSuccessful){
                updateReponse.postValue(response.body())
            } else{
                val errorBody = response.errorBody()?.string()
                Log.i("Delete", "Erreur updating ${errorBody}")
            }
        } catch (e:Exception){
            Log.i("MyApi", "update caught and error ${e.message}")
        }
    }

    val deleteFavResponse : MutableLiveData<FavDeleteResponse> = MutableLiveData()
    fun deleteFavourite(id: Int) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.deleteFav(id)
            if (response.isSuccessful){
                deleteFavResponse.postValue(response.body())
            } else{
                val errorBody = response.errorBody()?.string()
                Log.i("Delete", "Erreur deleting fav ${errorBody}")
            }
        } catch (e:Exception){
            Log.i("MyApi", "deleting fav caught error ${e.message}")
        }
    }

    val deleteIdentificatonResponse : MutableLiveData<DeleteIdentificationResponse> = MutableLiveData()
    fun deleteIdentification(id: Int)= viewModelScope.launch {
        try {
            val response = amaFloraRepository.deleteIdentification(id)
            if (response.isSuccessful){
                deleteIdentificatonResponse.postValue(response.body())
                Log.i("deleteIdentif", response.body()?.messgae.toString())
            } else {
                val errorBody = response.errorBody()?.string()
                Log.i("deleteIdentif", "Erreur deleting identification ${errorBody}")
            }
        } catch (e:Exception){
            Log.i("deleteIdentif", "deleting identification caught error ${e.message}")
        }
    }
}