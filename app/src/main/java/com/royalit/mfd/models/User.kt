package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("user_id" ) var userId : String? = null,
    @SerializedName("mobile"  ) var mobile : String? = null,
@SerializedName("email"   ) var email  : String? = null,
@SerializedName("profile_image"   ) var profile_image  : String? = null,
@SerializedName("full_name"   ) var full_name  : String? = null
) {


}