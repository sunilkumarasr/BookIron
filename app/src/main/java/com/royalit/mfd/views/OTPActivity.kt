package com.royalit.mfd.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityOtpactivityBinding
import com.royalit.mfd.models.LoginResponse
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.views.home.DashboardActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OTPActivity : AppCompatActivity() {
    lateinit var mobile:String
    lateinit var user_id:String
    lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mobile=intent.getStringExtra("mobile")!!
        user_id=intent.getStringExtra("user_id")!!
        binding.otpOne.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                binding.otpTwo.requestFocus()
            }

        })

        binding.otpTwo.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                    binding.otpThree.requestFocus()
                else
                    binding.otpOne.requestFocus()
            }

        })

        binding.otpThree.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                    binding.otpFour.requestFocus()
                else  binding.otpTwo.requestFocus()
            }

        })
        binding.otpFour.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                    binding.otpFive.requestFocus()
                else  binding.otpThree.requestFocus()
            }

        })


        binding.otpFive.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                    binding.otpSix.requestFocus()
                else  binding.otpFour.requestFocus()
            }

        })


        binding.otpSix.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>0)
                {}
                else  binding.otpFive.requestFocus()
            }

        })

    }
    fun otpVerify(view: View) {

        var otp=binding.otpOne.text.toString();
        otp=otp+binding.otpTwo.text.toString();
        otp=otp+binding.otpThree.text.toString();
        otp=otp+binding.otpFour.text.toString();
        otp=otp+binding.otpFive.text.toString();
        otp=otp+binding.otpSix.text.toString();
        if(otp.length<6)
        {
            Utils.showMessage("Please enter OTP", applicationContext)
            return
        }

        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",user_id);
        hashMap.putIfAbsent("otp_number",otp);

        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@OTPActivity,true)
        RetrofitClient.apiInterface.verifyOtp(hashMap).enqueue(object : Callback<LoginResponse> {




            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                customDialog.closeDialog()
                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());
                Utils.showMessage(strRes!!.message!!, applicationContext)
                if(strRes!!.status=="200")
                {
                    val gson= Gson()
                    Log.d("OBJECT TO JSON","ONECT TO JSON ${gson.toJson(strRes!!.data!!.userData)}")

                    if(strRes!!.data!!.userData?.userId==null)
                    {

                        return
                    }
                    MyPref.setUser(applicationContext,gson.toJson(strRes!!.data!!.userData))

                    //MyPref.setUser(applicationContext,strRes.toString())
                    val intent= Intent(applicationContext, DashboardActivity::class.java)
                    val mobile=strRes!!.data!!.userData!!.mobile
                    Log.d("mobile",mobile+"")
                    intent.putExtra("mobile",mobile)
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
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )
    }
    fun didnotreceive(view: View) {
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",user_id);


        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@OTPActivity,true)
        RetrofitClient.apiInterface.resendOtp(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()
                var strRes= response.body();
                Log.d("","Call succeess ${strRes}")
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }

                Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)



            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )
    }
}