package com.royalit.mfd.views.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.royalit.mfd.databinding.ActivityPlaceOrderBinding
import com.royalit.mfd.views.home.DashboardActivity

class PlaceOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var orderid=intent.getStringExtra("orderid").toString()
        binding.txtOrderId.setText("OrderID #$orderid")
    }

    fun savec(view: View) {
        CardData.cartListMap.value?.clear()
        val intent=Intent(applicationContext,DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}