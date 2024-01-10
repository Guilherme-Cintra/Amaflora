package ca.qc.castroguilherme.amaflorafb.models


import com.google.gson.annotations.SerializedName

data class WateringGeneralBenchmark(
    @SerializedName("unit")
    val unit: String,
    @SerializedName("value")
    val value: String
)