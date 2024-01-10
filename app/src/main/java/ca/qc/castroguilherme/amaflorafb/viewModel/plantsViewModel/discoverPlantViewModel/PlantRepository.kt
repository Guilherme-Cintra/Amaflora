package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel

import ca.qc.castroguilherme.amaflorafb.network.RetrofitInstance

class PlantRepository {

    suspend fun getAllPlants() = RetrofitInstance.retrofitService.getAll()

    suspend fun serachPlant(searchQuerry:String) = RetrofitInstance.retrofitService.getResearch(searchQuerry)
    suspend fun getDetail(id:Int) = RetrofitInstance.retrofitService.getDetails(id)
}