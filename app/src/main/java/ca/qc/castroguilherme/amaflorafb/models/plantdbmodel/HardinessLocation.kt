package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class HardinessLocation(
    @SerializedName("full_iframe")
    val fullIframe: String,
    @SerializedName("full_url")
    val fullUrl: String
)