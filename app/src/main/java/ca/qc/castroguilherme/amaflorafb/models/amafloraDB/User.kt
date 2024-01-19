package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profilePicture")
    val profilePicture: String,
    @SerializedName("uid")
    val uid: String
)