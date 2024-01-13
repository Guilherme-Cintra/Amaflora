package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class AllPlantsReponse(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("from")
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("to")
    val to: Int,
    @SerializedName("total")
    val total: Int
)