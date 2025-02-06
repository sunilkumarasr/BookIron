package com.royalit.mfd.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityChangePasswordBinding
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var binding:ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lnrBack.setOnClickListener {
            finish()
        }
    }

    fun send(view: View) {

        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj=JSONObject(userdataStr);

      //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        val mobile=jsobObj.getString("mobile");
       // val userdata=Gson().fromJson<LoginResponse>(userdataStr.toString(),LoginResponse::class.java)
        lateinit var user_id:String;
         var oldPassword=binding.edittextOldPassword.text.toString().trim();
         var newPassword=binding.edittextNewPassword.text.toString().trim();
         var conPassword=binding.edittextConfPassword.text.toString().trim();

        Log.d("User data","User data ${userId}}")
        //Log.d("User data","User data ${userdata!!.toString()}}")



        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",userId);
        hashMap.putIfAbsent("oldPassword",oldPassword);
        hashMap.putIfAbsent("newPassword",newPassword);
        hashMap.putIfAbsent("confirmPassword",conPassword);

        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        if(oldPassword.isEmpty())
        {
            Utils.showMessage("Please enter old password", applicationContext)
            return
        }
        if(newPassword.length<6)
        {
            Utils.showMessage("Password should be minimum 6 characters", applicationContext)
            return
        }
        if(!newPassword.equals(conPassword))
        {
            Utils.showMessage(
                "Confirm Password should should match with Password",
                applicationContext
            )
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@ChangePasswordActivity,true)
        RetrofitClient.apiInterface.changePassword(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()

                Log.d("strRes dssad asdas das ","asdsadas dasdasd "+response.message());
                var strRes= response.body() ;
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }

              Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {
                    finish()
                }


            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )
       // startActivity(Intent(applicationContext,LoginActivity::class.java))
       // finish()
    }
}