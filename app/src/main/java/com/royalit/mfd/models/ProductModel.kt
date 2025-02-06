package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class ProductModel(@SerializedName("id"             ) var id            : String? = null,
                        @SerializedName("category_id"    ) var categoryId    : String? = null,
                        @SerializedName("image"          ) var image         : String? = null,
                        @SerializedName("name"           ) var name          : String? = null,
                        @SerializedName("original_price" ) var originalPrice : String? = null,
                        @SerializedName("discount_price" ) var discountPrice : String? = null,
                        @SerializedName("description"    ) var description   : String? = null,
                        @SerializedName("status_id"      ) var statusId      : String? = null,
    ) {
}