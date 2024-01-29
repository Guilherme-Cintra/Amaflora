package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class UpdatePlantBody(
    @SerializedName("debut_darrosage_date")
    val debutDarrosageDate: String,
    @SerializedName("descriptionPerso")
    val descriptionPerso: String,
    @SerializedName("frequence_notif")
    val frequenceNotif: Int,
    @SerializedName("heure_notification")
    val heureNotification: String,
    @SerializedName("surnom")
    val surnom: String
)