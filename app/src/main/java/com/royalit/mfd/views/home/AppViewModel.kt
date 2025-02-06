package com.royalit.mfd.views.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.royalit.mfd.models.Categories
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class AppViewModel:ViewModel() {
    val categories=MutableLiveData<List<Categories>>()

    fun getCategories()
    {

        Log.d("strRes ","Calling api");
        RetrofitClient.apiInterface.serviceCategoryList().enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {


                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                if(jsonObject!!.get("status").asString=="200")
                {
                    val userListType: Type = object : TypeToken<List<Categories?>?>() {}.type

                    categories.value= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("service_categories_data"),userListType)
                    Log.d("categories.value","categories.value ${categories.value!!.size}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
            }

        })
    }
}