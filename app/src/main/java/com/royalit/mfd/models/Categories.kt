package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("id"               ) var id             : String? = null,
    @SerializedName("image"            ) var image          : String? = null,
    @SerializedName("name"             ) var name           : String? = null,
    @SerializedName("description"      ) var description    : String? = null,
    @SerializedName("price_per_weight" ) var pricePerWeight : String? = null,
    @SerializedName("status_id"        ) var statusId       : String? = null
) {
}