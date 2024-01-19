package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel

import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetHistoriqueBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.GetPlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.Recherche
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.network.RetrofitInstance

class AmaFloraRepository {
    suspend fun saveUser(user: User) = RetrofitInstance.retrofitAmafloraService.saveUser(user)
    suspend fun createPlant(createPlant: CreatePlant) = RetrofitInstance.retrofitAmafloraService.createPlant(createPlant)
    suspend fun getPlantById(id: Int) = RetrofitInstance.retrofitAmafloraService.getPlantById(id)
    suspend fun rechercheSauvegarde(recherche: RechercheBody) = RetrofitInstance.retrofitAmafloraService.sauvegarderRecherche(recherche)


    suspend fun getHistorique(uid: String) = RetrofitInstance.retrofitAmafloraService.getHistorique(uid)

    suspend fun getListPlants(uid: String)= RetrofitInstance.retrofitAmafloraService.getUserPlants(uid)
}