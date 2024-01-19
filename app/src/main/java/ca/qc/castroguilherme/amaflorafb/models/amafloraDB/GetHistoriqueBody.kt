package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class GetHistoriqueBody(
    @SerializedName("uid")
    val uid: String
)