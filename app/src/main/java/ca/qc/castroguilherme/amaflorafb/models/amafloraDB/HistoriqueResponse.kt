package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class HistoriqueResponse(
    @SerializedName("recherches")
    val recherches: List<RechercheX>
)