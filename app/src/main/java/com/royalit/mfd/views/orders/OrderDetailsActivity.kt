package com.royalit.mfd.views.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityOrderDetailsBinding
import com.royalit.mfd.models.OrderDetails
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailsBinding
    lateinit var orderDetailsAdapter: OrderDetailsSingleAdapter
    lateinit var orderid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderDetailsAdapter= OrderDetailsSingleAdapter(applicationContext)
        binding.recyclerOrderDetails.layoutManager=LinearLayoutManager(applicationContext)
        binding.recyclerOrderDetails.adapter=orderDetailsAdapter;
        orderid= intent.getStringExtra("orderid").toString()


        binding.lnrBack.setOnClickListener {
            finish()
        }
        getAOrderDetails()
    }
    fun getAOrderDetails()
    {
        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("order_id",orderid);
        hashMap.putIfAbsent("user_id",userId);
        hashMap.putIfAbsent("cat_id","1");
        val customDialog= CustomDialog(this@OrderDetailsActivity);
        customDialog.showDialog(this@OrderDetailsActivity,true)
        var request= RetrofitClient.apiInterface.orderDetails(hashMap )
        request.enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                customDialog.closeDialog()

                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                if(jsonObject!!.get("status").asString=="200")
                {

                    val activeOrderType: Type = object : TypeToken<List<OrderDetails>>() {}.type

                    var listOrderDetails:List<OrderDetails>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("order_details"),activeOrderType)

                    // activeOrderAdapter.setData(listActiveOrders!!)
                    listOrderDetails?.let {
                        Log.d("Order Details Data","Order Details Data ${listOrderDetails?.size}")
                        updateUI(listOrderDetails)
                    }

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })
    }
    fun updateUI(listOrderDetails:List<OrderDetails>)
    {
        if(listOrderDetails!=null&&listOrderDetails.size>0)
        {
            val orderDetails=listOrderDetails.get(0)
            binding.txtOrderId.text="Order #${orderDetails.orderId}"

            if(orderDetails.orderedDate!=null)
                binding.txtOrderDate.text="${ Utils.getCreatedDateFormat(orderDetails.orderedDate!!)}"
            else binding.txtOrderDate.text=""

            binding.txtSubTotal.text="₹ ${orderDetails.subTotal}"
            binding.txtTax.text="₹ ${orderDetails.tax}"
            binding.txtCostNo.text="₹ ${orderDetails.total}"
            binding.txtOrderStatus.text="${orderDetails.status}"

            orderDetailsAdapter.setData(orderDetails.products)
        }
    }
}