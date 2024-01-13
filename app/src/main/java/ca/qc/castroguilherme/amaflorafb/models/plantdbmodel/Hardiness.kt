package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class Hardiness(
    @SerializedName("max")
    val max: String,
    @SerializedName("min")
    val min: String
)