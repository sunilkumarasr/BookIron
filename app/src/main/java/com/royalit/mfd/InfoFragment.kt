package com.royalit.mfd

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.FragmentInfoBinding
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.Utils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var binding: FragmentInfoBinding
     var title="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       Log.e(" arguments?.getString(\"data\") ", arguments?.getString("data")!!)
        // Inflate the layout for this fragment
         title=arguments?.getString("data")!!
        binding=FragmentInfoBinding.inflate(layoutInflater)
        binding.txtHeader.text="${title}"
        binding.lnrBack.setOnClickListener {
            handleOnBackPressed()
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lnr_back=requireView().findViewById<View>(R.id.lnr_back)
        lnr_back.setOnClickListener {
            handleOnBackPressed()
        }
        lateinit var request:Call<Any>;


        if(title.contains("About Us"))
        {
            request= RetrofitClient.apiInterface.about()
        }else if(title.contains("Privacy Policy"))
        {
            request= RetrofitClient.apiInterface.privacy()

        }else if(title.contains("Terms & Conditions"))
        {
            request= RetrofitClient.apiInterface.termsConditions()
        }

        if(!Utils.checkConnectivity(requireActivity().applicationContext))
        {
            Utils.showMessage("Please check your connection ", requireActivity().applicationContext)
            return
        }
        val customDialog=CustomDialog(requireActivity());
        customDialog.showDialog(requireActivity(),true)
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
                    var data=""
                    if(title.contains("About Us"))
                    {
                        data=jsonObject!!.getAsJsonObject("data").getAsJsonArray("about_data").get(0).getAsJsonObject().get("about_content").asString
                    }else if(title.contains("Privacy Policy"))
                    {
                        data=jsonObject!!.getAsJsonObject("data").getAsJsonArray("privacy_data").get(0).getAsJsonObject().get("content").asString

                    }else if(title.contains("Terms & Conditions"))
                    {
                        data=jsonObject!!.getAsJsonObject("data").getAsJsonArray("terms_data").get(0).getAsJsonObject().get("content").asString

                    }
binding.webview.loadData(data,"text/html","utf-8");

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })
    }
    fun handleOnBackPressed(): Boolean {
        //Do your job here
        //use next line if you just need navigate up
        NavHostFragment.findNavController(this).navigateUp();
        //Log.e(getClass().getSimpleName(), "handleOnBackPressed");
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}