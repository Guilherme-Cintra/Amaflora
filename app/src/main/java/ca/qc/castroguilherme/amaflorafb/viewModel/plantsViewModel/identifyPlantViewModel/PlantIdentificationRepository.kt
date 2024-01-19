package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel

import ca.qc.castroguilherme.amaflorafb.network.RetrofitInstance
import okhttp3.MultipartBody

class PlantIdentificationRepository {
    suspend fun identify(project: String = "all",images: List<MultipartBody.Part>) = RetrofitInstance.retrofitIdentificationService.identify(project,images)
}