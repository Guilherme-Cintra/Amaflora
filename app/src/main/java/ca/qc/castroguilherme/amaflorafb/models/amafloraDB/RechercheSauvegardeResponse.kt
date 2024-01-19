package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class RechercheSauvegardeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("recherche")
    val recherche: Recherche?
)