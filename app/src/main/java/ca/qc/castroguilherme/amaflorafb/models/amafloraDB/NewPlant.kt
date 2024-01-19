package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class NewPlant(
    @SerializedName("description")
    val description: String,
    @SerializedName("freqEau")
    val freqEau: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("niveau_de_diff")
    val niveauDeDiff: String,
    @SerializedName("nomCommun")
    val nomCommun: String,
    @SerializedName("nomScientifique")
    val nomScientifique: String,
    @SerializedName("soleil")
    val soleil: String,
    @SerializedName("uniteFreqEau")
    val uniteFreqEau: String
)