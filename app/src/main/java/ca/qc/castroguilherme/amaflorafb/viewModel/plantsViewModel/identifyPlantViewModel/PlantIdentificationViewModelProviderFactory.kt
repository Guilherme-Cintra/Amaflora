package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.identifyPlantViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantIdentificationViewModelProviderFactory(private val plantIdentificationRepository: PlantIdentificationRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantIdentificationViewModel::class.java)){
            PlantIdentificationViewModel(plantIdentificationRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown viewModel class")
        }
    }

}