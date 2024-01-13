package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class WateringGeneralBenchmark(
    @SerializedName("unit")
    val unit: String,
    @SerializedName("value")
    val value: String
)