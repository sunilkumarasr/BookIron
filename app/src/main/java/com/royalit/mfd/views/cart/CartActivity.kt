package com.royalit.mfd.views.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.royalit.mfd.CartViewModel
import com.royalit.mfd.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var cartAdapter: CartAdapter
    lateinit var cartViewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cartViewModel=  ViewModelProvider.NewInstanceFactory().create(CartViewModel::class.java)

        binding.recyclerCart.layoutManager=LinearLayoutManager(applicationContext)
        cartAdapter= CartAdapter(this)
        binding.recyclerCart.adapter=cartAdapter
        binding.lnrBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        CardData.cartListMap?.observe(this, Observer {
            if(it.size>0)
            {
                binding.lnrBottomCart.visibility=View.VISIBLE
                binding.lnrEmpty.visibility=View.GONE
            }
            if(it.size==0)
            {
                binding.lnrEmpty.visibility=View.VISIBLE
                binding.lnrBottomCart.visibility=View.GONE
            }
            var totalItems=0;
            var totalPrice=0.0;
            var totalTax=0.0;
            it.forEach {
                totalItems=totalItems+it.quantity

                totalPrice=totalPrice+(it.price.toDouble()*it.quantity)
            }
            binding.txtTotalItems.text="$totalItems Items"
            binding.txtSubTotal.text="₹ $totalPrice"
            binding.txtTax.text="₹ $totalTax"
            binding.txtNetTotal.text="₹ ${totalTax+totalPrice}"
            binding.txtCostNo.text="₹ ${totalTax+totalPrice}"

            Log.d("Observer data","Observer data ${it.size}")
        })

    }

    fun shippingOrder(view: View) {
        startActivity(Intent(applicationContext,ShippingActivity::class.java))
    }
}