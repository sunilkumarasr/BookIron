package com.royalit.mfd.views.ui.account

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.royalit.mfd.R
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityAccountInfoBinding
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException


class AccountInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountInfoBinding
    var userId:String=""
    var mobile:String=""
    var userName:String=""
    var userProfileImage:String=""
    var base64Image=""
    var imageName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        userId=jsobObj.getString("user_id");
        mobile=jsobObj.getString("mobile");

        if(jsobObj.has("full_name"))
        {
            userName=jsobObj.getString("full_name");



        }
        fetProfile()
        if(userName==null)
            userName="Book Iron";
        if(jsobObj.has("profile_image"))
        {

            userProfileImage=jsobObj.getString("profile_image");
        }
        if(userProfileImage==null)
            userProfileImage=""
        binding.edittextName.setText("$userName")
        binding.edittextPhone.setText("$mobile")

        binding.imgEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            // set type
            // set type
            intent.type = "image/*"
            // start activity result
            // start activity result
            startActivityForResult(Intent.createChooser(intent, "Select Image"), 100)

        }

        Glide.with(this)

            .load("${RetrofitClient.CMS_IMAGE_PATH}/${userProfileImage}")
            .placeholder(getDrawable(R.drawable.img_account))

            .into(binding.imgProfile);


        binding.btnRegister.setOnClickListener {
            updateProfile()
        }

        binding.lnrBack.setOnClickListener {
            finish()
        }
    }

    fun updateProfile()
    {

        val mobile=binding.edittextPhone.text.toString().trim()
        val fullname=binding.edittextName.text.toString().trim()

        if(fullname.isEmpty())
        {
            Utils.showMessage("Enter full name", applicationContext)
            return
        }
        if(mobile.length<10)
        {
            Utils.showMessage("Enter valid mobile number", applicationContext)
            return
        }

        var gmail="$mobile@gmail.com";
        var hashMap = HashMap<String,String> ()
        hashMap.put("user_id",userId);
        hashMap.put("mobile",mobile);
        hashMap.put("email",gmail);
        hashMap.put("full_name",fullname);
        hashMap.put("profile_image",userProfileImage);


        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@AccountInfoActivity,true)
        RetrofitClient.apiInterface.updateProfile(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()
                var strRes= response.body();
                Log.d("","Call succeess ${response.code()}")

                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {
                    var userData= jsonObject!!.getAsJsonObject("data").getAsJsonObject("profile_data").toString()
                    Log.d("strRes ","Calling api 2 $userData");

                    MyPref.setUser(applicationContext,userData)
                    finish()

                }

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data==null)
            return
        val uri: Uri? = data?.getData()
        // Initialize bitmap
        // Initialize bitmap
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            // initialize byte stream
            val stream = ByteArrayOutputStream()
            // compress Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            // Initialize byte array
            val bytes = stream.toByteArray()
            // get base64 encoded string
            base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)


            val returnCursor: Cursor = contentResolver.query(uri!!, null, null, null, null)!!
            val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor!!.moveToFirst()
            imageName = returnCursor!!.getString(nameIndex)
            returnCursor!!.close()
            uploadIMage()


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun uploadIMage()
    {

        if(base64Image.isEmpty())
        {
            Utils.showMessage("Please try again",applicationContext)
            return
        }
        if(imageName.isEmpty())
        {
            imageName="$userName.jpeg"
        }

        var gmail="$mobile@gmail.com";
        var hashMap = HashMap<String,String> ()
        hashMap.put("user_id",userId);
        hashMap.put("file_name",imageName);
        hashMap.put("file_content",base64Image);


        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@AccountInfoActivity,true)
        RetrofitClient.apiInterface.uploadImage(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()
                var strRes= response.body();
                Log.d("","Call succeess ${response.code()}")

                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {

                    userProfileImage= jsonObject!!.getAsJsonObject("data").get("file_name").asString
                   // Log.d("strRes ","Calling api 2 $userData");

                   // MyPref.setUser(applicationContext,userData)
                   // finish()

                    //finish()
                    updateProfile()
                }

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )



    }

    fun fetProfile()
    {

        var hashMap = HashMap<String,String> ()
        hashMap.put("user_id",userId);


        if(!Utils.checkConnectivity(applicationContext))
        {
            Utils.showMessage("Please check your connection ", applicationContext)
            return
        }
        //showMessage("Calling ",applicationContext)
        val customDialog= CustomDialog(applicationContext);
        customDialog.showDialog(this@AccountInfoActivity,true)
        RetrofitClient.apiInterface.getProfile(hashMap).enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                customDialog.closeDialog()
                var strRes= response.body();
                Log.d("","Call succeess ${response.code()}")

                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {
                    var userData= jsonObject!!.getAsJsonObject("data").getAsJsonArray("profile_data").get(0).toString()
                    Log.d("strRes ","Calling api 2 $userData");

                    MyPref.setUser(applicationContext,userData)




                }

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                customDialog.closeDialog()
                Utils.showMessage("Try again", applicationContext)
            }

        }
        )
    }

}