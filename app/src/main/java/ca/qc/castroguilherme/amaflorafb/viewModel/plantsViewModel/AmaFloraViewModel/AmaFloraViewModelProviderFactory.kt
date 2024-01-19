package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AmaFloraViewModelProviderFactory(private val amaFloraRepository: AmaFloraRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AmaFloraViewModel::class.java)){
            AmaFloraViewModel(amaFloraRepository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown viewModel class")
        }
    }

}