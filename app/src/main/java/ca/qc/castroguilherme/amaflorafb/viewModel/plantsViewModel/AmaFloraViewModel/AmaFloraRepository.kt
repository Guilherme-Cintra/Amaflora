package ca.qc.castroguilherme.amaflorafb.viewModel.plantsViewModel.AmaFloraViewModel

import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreateIdentification
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.CreatePlant
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.FavBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.OwnedPlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.RechercheBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.UpdatePlantBody
import ca.qc.castroguilherme.amaflorafb.models.amafloraDB.User
import ca.qc.castroguilherme.amaflorafb.network.RetrofitInstance

class AmaFloraRepository {
    suspend fun saveUser(user: User) = RetrofitInstance.retrofitAmafloraService.saveUser(user)
    suspend fun createPlant(createPlant: CreatePlant) = RetrofitInstance.retrofitAmafloraService.createPlant(createPlant)
    suspend fun getPlantById(id: Int) = RetrofitInstance.retrofitAmafloraService.getPlantById(id)
    suspend fun rechercheSauvegarde(recherche: RechercheBody) = RetrofitInstance.retrofitAmafloraService.sauvegarderRecherche(recherche)


    suspend fun getHistorique(uid: String) = RetrofitInstance.retrofitAmafloraService.getHistorique(uid)

    suspend fun getListPlants(uid: String)= RetrofitInstance.retrofitAmafloraService.getUserPlants(uid)
    suspend fun getWishList(uid: String)= RetrofitInstance.retrofitAmafloraService.getWishList(uid)
    suspend fun getIdentificationsList(uid: String)= RetrofitInstance.retrofitAmafloraService.getIdentifications(uid)
    suspend fun deleteIdentification(id: Int) = RetrofitInstance.retrofitAmafloraService.deleteIdentification(id)

    suspend fun postIdentification(createIdentification: CreateIdentification) = RetrofitInstance.retrofitAmafloraService.postIdentification(createIdentification)

    suspend fun postFavourite(favBody: FavBody) = RetrofitInstance.retrofitAmafloraService.addToFav(favBody)
    suspend fun deleteFav(id: Int) = RetrofitInstance.retrofitAmafloraService.deleteFavourite(id)
    suspend fun addOwnedPlant(ownedPlantBody: OwnedPlantBody) = RetrofitInstance.retrofitAmafloraService.addOwnedPlant(ownedPlantBody)

    suspend fun deleteOwnedPlant(id: Int) = RetrofitInstance.retrofitAmafloraService.deleteOwnedPlant(id)

    suspend fun updatePlant(id: Int, updatePlantBody: UpdatePlantBody) = RetrofitInstance.retrofitAmafloraService.updatePlant(id, updatePlantBody)
}
