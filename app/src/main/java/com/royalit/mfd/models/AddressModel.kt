package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("id"            ) var id           : String? = null,
    @SerializedName("user_id"       ) var userId       : String? = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("mobile_number" ) var mobileNumber : String? = null,
    @SerializedName("house_number"  ) var houseNumber  : String? = null,
    @SerializedName("landmark"      ) var landmark     : String? = null,
    @SerializedName("city"          ) var city         : String? = null,
    @SerializedName("state"         ) var state        : String? = null,
    @SerializedName("country"       ) var country      : String? = null,
    @SerializedName("pincode"       ) var pincode      : String? = null,
    var isChecked      : Boolean = false
) {
}