package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetHistoriqueBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetPlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetPlantResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.HistoriqueResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.ListPlants
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Plant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Recherche
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheSauvegardeResponse
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UserResponse
import ca.qc.castroguilherme.amaflorafb.models.identificationmodels.ErrorResponse
import ca.qc.castroguilherme.amaflorafb.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    fun createPlant(createPlant: CreatePlant) = viewModelScope.launch {
        try {
            val response = amaFloraRepository.createPlant(createPlant)
            if (response.isSuccessful) {
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
                Log.e("MyApi", "Error: $errorBody")


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


                val filteredPlants = plants.distinct()
                plantsLiveData.postValue(filteredPlants)

            } else {
                Log.i("MyApi", "erreur ${response.body()}")
                val errorBody = response.errorBody()?.string()
                Log.e("MyApi", "Error: $errorBody")

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
                Log.e("MyApi", "Error: $errorBody")

                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e: Exception) {
            Log.i("MyApi", "user's plants caught an error ${e.message}")
        }
    }
}