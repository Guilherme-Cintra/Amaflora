package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class ListPlantsItem(
    @SerializedName("debut_darrosage_date")
    val debutDarrosageDate: String,
    @SerializedName("descriptionPerso")
    val descriptionPerso: String,
    @SerializedName("frequence_notif")
    val frequenceNotif: Int,
    @SerializedName("heure_notification")
    val heureNotification: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("nomReel")
    val nomReel: String,
    @SerializedName("plantId")
    val plantId: Int,
    @SerializedName("surnom")
    val surnom: String,
    @SerializedName("userUid")
    val userUid: String
)