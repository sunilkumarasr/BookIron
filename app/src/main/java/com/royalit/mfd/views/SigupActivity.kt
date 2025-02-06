package com.royalit.mfd.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivitySigupBinding
import com.royalit.mfd.models.LoginResponse
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.utils.Utils.Companion.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySigupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySigupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun register(view: View) {
        val mobile=binding.edittextPhone.text.toString().trim()
        val password=binding.edittextPassword.text.toString().trim()
        val confirmPass=binding.edittextConfPassword.text.toString().trim()
        val fullname=binding.edittextName.text.toString().trim()

        if(fullname.isEmpty())
        {
            showMessage("Enter full name",applicationContext)
            return
        }
        if(mobile.length<10)
        {
            showMessage("Enter valid mobile number",applicationContext)
            return
        }
        if(password.length<6)
        {
            showMessage("Password should be minimum  6 characters",applicationContext)
            return
        }
        if(!password.equals(confirmPass))
        {
            showMessage("Confirm Password should should match with Password",applicationContext)
            return
        }
        var gmail="$mobile@gmail.com";
        var hashMap = HashMap<String,String> ()
        hashMap.put("mobile",mobile);
        hashMap.put("password",password);
        hashMap.put("email",gmail);
       // hashMap.put("email_",gmail);
        hashMap.put("full_name",fullname);


        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@SigupActivity,true)
        RetrofitClient.apiInterface.register(hashMap).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                customDialog.closeDialog()
                var strRes= response.body();
                Log.d("","Call succeess ${response.code()}")

                if(response.code()!=200)
                {
                    Utils.showMessage("Please try again", applicationContext)

                    return
                }
                Log.d("","Call succeess ${strRes}")
                Utils.showMessage(strRes!!.message!!, applicationContext)
                 if(strRes!!.status=="200")
                {


                   // MyPref.setUser(applicationContext,gson.toJson(strRes!!.data!!.userData))
                    val intent= Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )

    }

    override fun onBackPressed() {

        startActivity(Intent(applicationContext,LoginActivity::class.java))
        finish()
    }
}

