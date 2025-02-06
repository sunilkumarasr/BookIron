package com.royalit.mfd.views.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.royalit.mfd.R
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityOrdersListBinding
import com.royalit.mfd.models.ActiveOrder
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.views.cart.CartActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class OrdersListActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrdersListBinding
    lateinit var category_id:String
    lateinit var serviceListFragmentAdapter: ServiceListFragmentAdapter
    val samplCategoryList= listOf<String>(
        "Men",
        "Women",
        "Kids",
        "Others",
        "Men",
        "Women",
        "Kids",
        "Others",
        "Men",
        "Women",

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrdersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        serviceListFragmentAdapter= ServiceListFragmentAdapter(supportFragmentManager)
        binding.viewpager.offscreenPageLimit=10
        category_id="0"
        category_id=  intent.getStringExtra("category_id").toString()
        if(category_id==null)
            category_id="0"
        binding.tablayout.setupWithViewPager(binding.viewpager)

        binding.viewpager.addOnPageChangeListener(object: OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        binding.tablayout.addOnTabSelectedListener(object:OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabSelection(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.viewpager.adapter=serviceListFragmentAdapter
        tabSelection(0)
        binding.lnrBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
    fun tabSelection(pos:Int)
    {
        for (i in 0..9) {
            val layout=layoutInflater.inflate(R.layout.layout_tab_items,null)
            val txt=layout.findViewById<TextView>(R.id.txt_tab_header) as CheckedTextView
            txt.setText("${samplCategoryList[i]}")
            txt.isChecked=(i==pos);
            binding.tablayout.getTabAt(i)!!.setCustomView(layout)
            //binding.tablayout.addTab(binding.tablayout.newTab().setText("Page: $i"))
        }
        getProducts()
    }

    fun confirmOrder(view: View) {
        startActivity(Intent(applicationContext,CartActivity::class.java))
    }
   fun getProducts()
    {

        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("category_id",category_id);
        val customDialog= CustomDialog(this@OrdersListActivity);
        customDialog.showDialog(this@OrdersListActivity,true)
        var request= RetrofitClient.apiInterface.productList(hashMap )
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

                    val activeOrderType: Type = object : TypeToken<List<ActiveOrder>>() {}.type

                    var listActiveOrders:List<ActiveOrder>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("users_active_order_data"),activeOrderType)


                    listActiveOrders.let {
                        Log.d("listActiveOrders","listActiveOrders ${listActiveOrders?.size}")
                    }                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })
    }
}