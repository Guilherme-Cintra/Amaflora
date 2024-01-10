package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import ca.qc.castroguilherme.amaflorafb.models.AllPlantsReponse
import ca.qc.castroguilherme.amaflorafb.models.DetailResponse


class PlantsViewModel(private val plantsRepository: PlantRepository):ViewModel() {
    val allPlants: MutableLiveData<AllPlantsReponse> = MutableLiveData()
    val detailPlant: MutableLiveData<DetailResponse> = MutableLiveData()

    fun getAllPlants() = viewModelScope.launch {
        try {
            val response = plantsRepository.getAllPlants()
            if (response.isSuccessful){
                allPlants.postValue(response.body())
            }
        }catch (e: Exception){
            Log.i("Error line 22", e.message.toString())
        }
    }

    fun searchPlant(searchQuerry: String) = viewModelScope.launch {
        try {
            val response = plantsRepository.serachPlant(searchQuerry)
            if (response.isSuccessful){
                allPlants.postValue(response.body())
            }
        }catch (e: Exception){
            Log.i("E33", e.message.toString())
        }
    }


    fun getDetail(id:Int) = viewModelScope.launch {
        try {
            val response = plantsRepository.getDetail(id)
            if (response.isSuccessful){
                detailPlant.postValue(response.body())
            }
        }catch (e: Exception){
            Log.i("E47", e.message.toString())
        }
    }

}