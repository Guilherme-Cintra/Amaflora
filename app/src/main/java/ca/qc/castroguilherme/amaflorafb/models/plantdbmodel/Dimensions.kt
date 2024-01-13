package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class Dimensions(
    @SerializedName("max_value")
    val maxValue: Int,
    @SerializedName("min_value")
    val minValue: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("unit")
    val unit: String
)