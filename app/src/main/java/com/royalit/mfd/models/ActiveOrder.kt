package com.royalit.mfd.models

import com.google.gson.annotations.SerializedName

data class ActiveOrder(@SerializedName("id"                     ) var id                   : String? = null,
                       @SerializedName("user_id"                ) var userId               : String? = null,
                       @SerializedName("address_id"             ) var addressId            : String? = null,
                       @SerializedName("card_id"                ) var cardId               : String? = null,
                       @SerializedName("payment_method_id"      ) var paymentMethodId      : String? = null,
                       @SerializedName("order_from"             ) var orderFrom            : String? = null,
                       @SerializedName("unique_id"              ) var uniqueId             : String? = null,
                       @SerializedName("sub_total"              ) var subTotal             : String? = null,
                       @SerializedName("tax"                    ) var tax                  : String? = null,
                       @SerializedName("total"                  ) var total                : String? = null,
                       @SerializedName("payment_status"         ) var paymentStatus        : String? = null,
                       @SerializedName("expected_pickup_date"   ) var expectedPickupDate   : String? = null,
                       @SerializedName("expected_delivery_date" ) var expectedDeliveryDate : String? = null,
                       @SerializedName("ordered_date"           ) var orderedDate          : String? = null,
                       @SerializedName("order_id"               ) var orderId              : String? = null,
                       @SerializedName("status_id"              ) var statusId             : String? = null,
                       @SerializedName("date_time"              ) var dateTime             : String? = null,
                       @SerializedName("status"                 ) var status               : String? = null

) {
}