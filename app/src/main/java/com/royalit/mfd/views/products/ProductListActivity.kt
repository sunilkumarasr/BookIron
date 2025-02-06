package com.royalit.mfd.views.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.royalit.mfd.CartViewModel
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityProductListBinding
import com.royalit.mfd.models.ProductModel
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.views.cart.CardData
import com.royalit.mfd.views.cart.CartActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ProductListActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductListBinding

    lateinit var orderListAdapter: ProductListAdapter
    lateinit var category_id:String
    lateinit var cartViewModel:CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category_id=intent.getStringExtra("category_id").toString()
        binding.recyclerProdcutList.layoutManager= LinearLayoutManager(applicationContext)
        orderListAdapter=ProductListAdapter(this)
        binding.recyclerProdcutList.adapter=orderListAdapter
        cartViewModel=  ViewModelProvider.NewInstanceFactory().create(CartViewModel::class.java)
        getProducts()
        binding.lnrBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        CardData.cartListMap?.observe(this, Observer {
            if(it.size>0&&binding.lnrTotal.visibility!=View.VISIBLE)
            {
                binding.lnrTotal.visibility=View.VISIBLE
            }
            if(it.size==0)
            {
                binding.lnrTotal.visibility=View.GONE
            }
            var totalItems=0;
            var totalPrice=0.0;
            it.forEach {
                totalItems=totalItems+it.quantity

                totalPrice=totalPrice+(it.price.toDouble()*it.quantity)
            }
            binding.txtTotalItems.text="$totalItems Items"
            binding.txtCostNo.text="₹ $totalPrice"

            Log.d("Observer data","Observer data ${it.size}")
        })
    }
    fun getProducts()
    {
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("category_id",category_id);

        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@ProductListActivity,true)
        Log.d("strRes ","Calling api");
        RetrofitClient.apiInterface.productList(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                customDialog.closeDialog()

                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                if(jsonObject!!.get("status").asString=="200")
                {
                    val userListType: Type = object : TypeToken<List<ProductModel?>?>() {}.type

                  val  categories= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("products_data"),userListType) as List<ProductModel>
                    Log.d("categories.value","categories.value ${categories!!.size}")
                    orderListAdapter.setData(categories)
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Log.d("strRes ","Calling api 3");
            }

        })
    }
    fun confirmOrder(view: View) {
        startActivity(Intent(applicationContext, CartActivity::class.java))
    }
}