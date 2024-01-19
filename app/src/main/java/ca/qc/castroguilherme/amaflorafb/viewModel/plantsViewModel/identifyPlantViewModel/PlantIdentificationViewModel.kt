package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.castroguilherme.amaflora.models.identificationmodels.IdentificationResponse
import ca.qc.castroguilherme.amaflorafb.models.identificationmodels.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PlantIdentificationViewModel(private val plantIdentificationRepository: PlantIdentificationRepository): ViewModel() {
    val identificationResponse: MutableLiveData<IdentificationResponse> = MutableLiveData()

    fun identify(project: String = "all",images: List<MultipartBody.Part>) = viewModelScope.launch {
        try {
            val response = plantIdentificationRepository.identify(project, images)
            if (response.isSuccessful) {
                identificationResponse.postValue(response.body())
                Log.i("Planta", " line 19 : ${response.body()?.bestMatch}")
//                Log.i("Planta", " line 19 : ${response.body()?.message}")

            }
            else {

                val errorBody = response.errorBody()?.string()
                Log.e("Planta", "Error: $errorBody")


                try {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                } catch (e: JsonSyntaxException) {

                }
            }
        } catch (e: Exception) {
            Log.i("Planta", "error line 26${e.message.toString()}")
        }
    }
}