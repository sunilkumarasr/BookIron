package com.royalit.mfd.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityLoginBinding
import com.royalit.mfd.models.LoginResponse
import com.royalit.mfd.services.RetrofitClient.apiInterface
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.utils.Utils.Companion.checkConnectivity
import com.royalit.mfd.utils.Utils.Companion.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun login(view: View) {
val mobile_number=binding.edittextPhone.text.toString().trim()
val password=binding.edittextPassword.text.toString().trim()
        if(mobile_number.length<10)
        {
            Utils.showMessage("Enter valid mobile number", applicationContext)
            return
        }
        if(password.isEmpty())
        {
            Utils.showMessage("Please enter password", applicationContext)
            return
        }

        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("mobile",mobile_number);
        hashMap.putIfAbsent("password",password);

        if(!checkConnectivity(applicationContext))
        {
            showMessage("Please check your connection ",applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
    val customDialog=CustomDialog(applicationContext);
        customDialog.showDialog(this@LoginActivity,true)
         apiInterface.login(hashMap).enqueue(object : Callback<LoginResponse> {

             override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                 customDialog.closeDialog()
                 var strRes= response.body();
                 //strRes=strRes.replace("!!","")
                 Log.d("strRes ",strRes.toString());
                 showMessage(strRes!!.message!!,applicationContext)
                 if(strRes!!.status=="200")
                 {
                     val intent=Intent(applicationContext,OTPActivity::class.java)
                    val mobile=strRes!!.data!!.userData!!.mobile
                    val user_id=strRes!!.data!!.userData!!.userId
                     Log.d("mobile",mobile+"")
                     intent.putExtra("mobile",mobile)
                     intent.putExtra("user_id",user_id)
                     startActivity(intent)
                     finish()
                     return
                 }
                /* val gson=Gson()
                val loginResponse= gson.fromJson<LoginResponse>(strRes, LoginResponse::class.java)
               */  Log.d("","Call succeess ${strRes!!.status}")

             }

             override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                 customDialog.closeDialog()
                 showMessage("Try again",applicationContext)
             }

         }
         )
       // startActivity(Intent(applicationContext,OTPActivity::class.java))
    }

    fun register(view: View) {
        startActivity(Intent(applicationContext,SigupActivity::class.java))
        finish()
    }

    fun fbclick(view: View) {}
    fun gmailClikc(view: View) {}
    fun forgotpass(view: View) {
        startActivity(Intent(applicationContext,ForgotActivity::class.java))
        finish()
    }



}