package com.royalit.mfd.views.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.royalit.mfd.R
import com.royalit.mfd.databinding.ActivityDashboardBinding
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.views.ChangePasswordActivity
import com.royalit.mfd.views.LoginActivity
import com.royalit.mfd.views.cart.AddressListActivity
import com.royalit.mfd.views.cart.CartActivity
import com.royalit.mfd.views.ui.ContactUs
import com.royalit.mfd.views.ui.account.AccountInfoActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
val appViewModel:AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            val intent=Intent(applicationContext, CartActivity::class.java)
            startActivity(intent)
        }
        appViewModel.getCategories()
    }
    fun accountInfo(view: View) {
        var intent=Intent(applicationContext,AccountInfoActivity::class.java)
        startActivity(intent)
    }
    fun myaddress(view: View) {
        var intent=Intent(applicationContext,AddressListActivity::class.java)
        intent.putExtra("showButton",false)
        startActivity(intent)
    }
    fun aboutus(view: View) {
        val bundle=Bundle()
        bundle.putString("data","About Us")
        Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_infoFragment,bundle);

    }
    fun contactus(view: View) {
        val bundle=Bundle()
        bundle.putString("data","Contact US")
        startActivity(Intent(applicationContext, ContactUs::class.java))
       // Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_infoFragment,bundle);

    }
    fun privacy(view: View) {
        val bundle=Bundle()
        bundle.putString("data","Privacy Policy")
        Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_infoFragment,bundle);

    }
    fun changepassword(view: View) {

        startActivity(Intent(applicationContext,ChangePasswordActivity::class.java))
       // Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_infoFragment,bundle);

    }
    fun termscondition(view: View) {
        val bundle=Bundle()
        bundle.putString("data","Terms & Conditions")
        Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_infoFragment,bundle);
    }
    fun logout(view: View) {
        MyPref.clear(applicationContext)
        val intent=Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}