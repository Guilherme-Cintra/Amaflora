package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.discoverPlantViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantViewModelProviderFactory(private val plantRepository: PlantRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantsViewModel::class.java)){
            PlantsViewModel(plantRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown viewModel class")
        }
    }

}