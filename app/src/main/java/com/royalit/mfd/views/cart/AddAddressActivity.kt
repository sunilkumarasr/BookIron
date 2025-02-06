package com.royalit.mfd.views.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityAddAddressBinding
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

class AddAddressActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun saveaddress(view: View){
        var house_number=binding.edittextHno.text.toString().trim()
        var landmark=binding.edittextNearLocation.text.toString().trim()
        var city=binding.edittextCity.text.toString().trim()
        var state=binding.edittextState.text.toString().trim()
        var country=binding.edittextCountry.text.toString().trim()
        var pincode=binding.edittextPincode.text.toString().trim()


        if(
            house_number.isEmpty()||landmark.isEmpty()||
            city.isEmpty()||state.isEmpty()||
            country.isEmpty()||pincode.isEmpty()
            )
        {
            Utils.showMessage("Fill all details",applicationContext)
            return
        }


        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        val mobile_number=jsobObj.getString("mobile");
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",userId);
        hashMap.putIfAbsent("cat_id","1");
        hashMap.putIfAbsent("house_number",house_number);
        hashMap.putIfAbsent("landmark",landmark);
        hashMap.putIfAbsent("city",city);
        hashMap.putIfAbsent("state",state);
        hashMap.putIfAbsent("country",country);
        hashMap.putIfAbsent("pincode",pincode);
        hashMap.putIfAbsent("mobile_number",mobile_number);
        hashMap.putIfAbsent("name","");
        val customDialog= CustomDialog(this@AddAddressActivity);
        customDialog.showDialog(this@AddAddressActivity,true)
        var request= RetrofitClient.apiInterface.addAddress(hashMap )
        request.enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                customDialog.closeDialog()

                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {

                    val activeOrderType: Type = object : TypeToken<List<OrderDetails>>() {}.type

                    var listOrderDetails:List<OrderDetails>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("order_details"),activeOrderType)

                    // activeOrderAdapter.setData(listActiveOrders!!)
                    listOrderDetails.let {
                        Log.d("Order Details Data","Order Details Data ${listOrderDetails?.size}")
                    }
                    finish()

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })

    }
}