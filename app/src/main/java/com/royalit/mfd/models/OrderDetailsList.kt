package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName


data class Products (

    @SerializedName("id"           ) var id          : String? = null,
    @SerializedName("order_id"     ) var orderId     : String? = null,
    @SerializedName("product_id"   ) var productId   : String? = null,
    @SerializedName("price"        ) var price       : String? = null,
    @SerializedName("quantity"     ) var quantity    : String? = null,
    @SerializedName("product_name" ) var productName : String? = null,
    @SerializedName("image"        ) var image       : String? = null

)

data class AddressDetailsOrder (

    @SerializedName("house_number" ) var houseNumber : String? = null,
    @SerializedName("landmark"     ) var landmark    : String? = null,
    @SerializedName("city"         ) var city        : String? = null,
    @SerializedName("state"        ) var state       : String? = null,
    @SerializedName("country"      ) var country     : String? = null

)

data class OrderDetails (

    @SerializedName("id"                     ) var id                   : String?             = null,
    @SerializedName("user_id"                ) var userId               : String?             = null,
    @SerializedName("address_id"             ) var addressId            : String?             = null,
    @SerializedName("card_id"                ) var cardId               : String?             = null,
    @SerializedName("payment_method_id"      ) var paymentMethodId      : String?             = null,
    @SerializedName("order_from"             ) var orderFrom            : String?             = null,
    @SerializedName("unique_id"              ) var uniqueId             : String?             = null,
    @SerializedName("sub_total"              ) var subTotal             : String?             = null,
    @SerializedName("tax"                    ) var tax                  : String?             = null,
    @SerializedName("total"                  ) var total                : String?             = null,
    @SerializedName("payment_status"         ) var paymentStatus        : String?             = null,
    @SerializedName("expected_pickup_date"   ) var expectedPickupDate   : String?             = null,
    @SerializedName("expected_delivery_date" ) var expectedDeliveryDate : String?             = null,
    @SerializedName("ordered_date"           ) var orderedDate          : String?             = null,
    @SerializedName("payment_flag"           ) var paymentFlag          : String?             = null,
    @SerializedName("order_completion_flag"  ) var orderCompletionFlag  : String?             = null,
    @SerializedName("order_id"               ) var orderId              : String?             = null,
    @SerializedName("full_name"              ) var fullName             : String?             = null,
    @SerializedName("email"                  ) var email                : String?             = null,
    @SerializedName("mobile"                 ) var mobile               : String?             = null,
    @SerializedName("password"               ) var password             : String?             = null,
    @SerializedName("firebase_token"         ) var firebaseToken        : String?             = null,
    @SerializedName("created_at"             ) var createdAt            : String?             = null,
    @SerializedName("name"                   ) var name                 : String?             = null,
    @SerializedName("mobile_number"          ) var mobileNumber         : String?             = null,
    @SerializedName("house_number"           ) var houseNumber          : String?             = null,
    @SerializedName("landmark"               ) var landmark             : String?             = null,
    @SerializedName("city"                   ) var city                 : String?             = null,
    @SerializedName("state"                  ) var state                : String?             = null,
    @SerializedName("country"                ) var country              : String?             = null,
    @SerializedName("status"                ) var status              : String?             = null,
    @SerializedName("pincode"                ) var pincode              : String?             = null,
    @SerializedName("products"               ) var products             : ArrayList<Products> = arrayListOf(),
    @SerializedName("address_details"        ) var addressDetails       : AddressDetailsOrder?     = AddressDetailsOrder()

)