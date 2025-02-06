package com.royalit.mfd.views.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.adapters.ActiveOrderAdapter
import com.royalit.mfd.adapters.ServiceCategoryAdapter
import com.royalit.mfd.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var serviceCategoryAdapter: ServiceCategoryAdapter
    lateinit var activeOrderAdapter: ActiveOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        serviceCategoryAdapter= ServiceCategoryAdapter()
        activeOrderAdapter= ActiveOrderAdapter(resources)
        binding.recyclerServices.layoutManager=LinearLayoutManager(applicationContext,RecyclerView.HORIZONTAL,false)
        binding.recyclerActiveOrder.layoutManager=LinearLayoutManager(applicationContext)
        binding.recyclerServices.adapter=serviceCategoryAdapter
        binding.recyclerActiveOrder.adapter=activeOrderAdapter
    }
}