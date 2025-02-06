package com.royalit.mfd.views.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityAddressListBinding
import com.royalit.mfd.models.AddressModel
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

class AddressListActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddressListBinding
    lateinit var addressListAdapter: AddressListAdapter
     var addressModel: AddressModel?=null
     var showButton=true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showButton=intent.getBooleanExtra("showButton",true)

        if(!showButton)
        {
            binding.btnSubmit.visibility=View.GONE
        }else binding.btnSubmit.visibility=View.VISIBLE

        binding.recyclerviewAddress.layoutManager=LinearLayoutManager(applicationContext)
        addressListAdapter= AddressListAdapter(this,showButton)
        binding.recyclerviewAddress.adapter=addressListAdapter
        binding.lnrBack.setOnClickListener {
            finish()
        }


        binding.fab.setOnClickListener {
            startActivityForResult(Intent(applicationContext,AddAddressActivity::class.java),200)
        }

        loadAddresses()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadAddresses()
    }
    fun submit(view: View) {
        if(addressModel!=null) {
            addressModel?.let {
                intent.putExtra("hno", addressModel!!.houseNumber)
                intent.putExtra("landmark", addressModel!!.landmark)
                intent.putExtra("city", addressModel!!.city)
                intent.putExtra("state", addressModel!!.state)
                intent.putExtra("country", addressModel!!.country)
                intent.putExtra("pincode", addressModel!!.pincode)
                intent.putExtra("id", addressModel!!.id)
                setResult(200, intent)
                finish()
            }

        }else
        {
            Utils.showMessage("Please select/add address",applicationContext)
        }
    }
    fun loadAddresses()
    {
        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",userId);
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@AddressListActivity,true)
        RetrofitClient.apiInterface.addressList(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()
                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                if(jsonObject!!.get("status").asString=="200")
                {

                    val activeOrderType: Type = object : TypeToken<List<AddressModel>>() {}.type

                    var listAddress:List<AddressModel>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("address_list"),activeOrderType)

                    // activeOrderAdapter.setData(listActiveOrders!!)
                    listAddress.let {
                        Log.d("Order Details Data","Order Details Data ${listAddress?.size}")
                        addressListAdapter.setData(listAddress)
                    }


                }


            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        })
    }

    fun selectedAddress(selectedaddressModel: AddressModel) {

        addressModel=selectedaddressModel

    }
}