package com.royalit.mfd.views.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.adapters.ActiveOrderAdapter
import com.royalit.mfd.adapters.ServiceCategoryAdapter
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.FragmentHomeBinding
import com.royalit.mfd.models.ActiveOrder
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.views.home.AppViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var serviceCategoryAdapter: ServiceCategoryAdapter
    lateinit var activeOrderAdapter: ActiveOrderAdapter
    private val sharedViewModel: AppViewModel by activityViewModels()
     var userId:String=""
     var userName:String?=null
     var userProfileImage:String?=null
    var bannerImage:String=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        serviceCategoryAdapter= ServiceCategoryAdapter()
        activeOrderAdapter= ActiveOrderAdapter(resources)
        binding.recyclerServices.layoutManager=
            LinearLayoutManager(requireActivity().applicationContext, RecyclerView.HORIZONTAL,false)
        binding.recyclerActiveOrder.layoutManager= LinearLayoutManager(requireActivity().applicationContext)
        binding.recyclerServices.adapter=serviceCategoryAdapter
        binding.recyclerActiveOrder.adapter=activeOrderAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.categories.observe(requireActivity(), Observer {
            Log.d("Categories List","Categories List ${it.size}" )

            serviceCategoryAdapter.setData(it)
        })

        val userdataStr= MyPref.getUser(requireActivity().applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        userId=jsobObj.getString("user_id");

        if(jsobObj.has("full_name"))
        {
            userName=jsobObj.getString("full_name");



        }
        if(userName==null)
            userName="Book Iron";
        if(jsobObj.has("profile_image"))
        {

            userProfileImage=jsobObj.getString("profile_image");
        }
        if(userProfileImage==null)
            userProfileImage=""
        binding.txtName.text="Hi, $userName"
        Glide.with(this)

            .load("${RetrofitClient.CMS_IMAGE_PATH}/${userProfileImage}")
            .placeholder(requireActivity().getDrawable(R.drawable.img_account))

            .into(binding.imgProfile);
        getActiveORDERS()
        if(bannerImage==null||bannerImage.isEmpty())
        getBaners()

        updateBanners()
    }

    private fun getBaners() {
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",userId);
       // val customDialog= CustomDialog(requireActivity().applicationContext);
       // customDialog.showDialog(requireActivity(),true)
        var request= RetrofitClient.apiInterface.about( )
        request.enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {
              //  customDialog.closeDialog()

                var strRes= response.body();
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


                if(jsonObject!!.get("status").asString=="200")
                {


                    bannerImage=jsonObject!!.getAsJsonObject("data").getAsJsonArray("about_data").get(0).getAsJsonObject().get("about_image").asString

                    updateBanners()

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");

            }

        })
    }

    fun getActiveORDERS()
    {
        /*val userdataStr= MyPref.getUser(requireActivity().applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");*/
        var hashMap = HashMap<String, String> (2)
        hashMap.putIfAbsent("user_id",userId);
        val customDialog= CustomDialog(requireActivity().applicationContext);
        customDialog.showDialog(requireActivity(),true)
        var request= RetrofitClient.apiInterface.activeOrders(hashMap )
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

                    val activeOrderType: Type = object : TypeToken<List<ActiveOrder>>() {}.type

                    var listActiveOrders:List<ActiveOrder>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("users_active_order_data"),activeOrderType)

                    activeOrderAdapter.setData(listActiveOrders!!)
                    listActiveOrders.let {
                        Log.d("listActiveOrders","listActiveOrders ${listActiveOrders?.size}")
                    }

                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   fun updateBanners()
    {
        if(bannerImage==null||bannerImage.isEmpty())
        {

            return
        }

        Glide.with(this)

            .load("${RetrofitClient.CMS_IMAGE_PATH}/${bannerImage}")
            .placeholder(requireActivity().getDrawable(R.drawable.img_home_banner))

            .into(binding.imgBanner);
    }
}