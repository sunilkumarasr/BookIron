package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("status"  ) var status  : String? = null,
                         @SerializedName("message" ) var message : String? = null,
                         @SerializedName("data"    ) var data    : Data?   = Data()) {
}


data class Data (

    @SerializedName("user_data" ) var userData : User? = User()

)