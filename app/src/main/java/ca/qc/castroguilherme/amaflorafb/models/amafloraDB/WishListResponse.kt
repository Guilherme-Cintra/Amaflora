package ca.qc.castroguilherme.amaflorafb.models.amafloraDB


import com.google.gson.annotations.SerializedName

data class WishListResponse(
    @SerializedName("wishList")
    val wishList: List<Wish>
)