package com.royalit.mfd.views.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.FragmentOrdersBinding
import com.royalit.mfd.models.OrderDetailModel
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentOrdersBinding
    lateinit var oRdersAdapter: ORdersAdapter

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
        // Inflate the layout for this fragment
        binding=FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oRdersAdapter= ORdersAdapter()
        binding.recyclervieworder.layoutManager=LinearLayoutManager(context)
        binding.recyclervieworder.adapter=oRdersAdapter
        binding.lnrEmpty.visibility=View.VISIBLE
        getMyOrders()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun getMyOrders()
    {
        val userdataStr= MyPref.getUser(requireActivity())

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        var hashMap = HashMap<String, String> (2)

        hashMap.putIfAbsent("user_id",userId);
        val customDialog= CustomDialog(requireActivity());
        customDialog.showDialog(requireActivity(),true)
        var request= RetrofitClient.apiInterface.orders(hashMap )
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

                    val activeOrderType: Type = object : TypeToken<List<OrderDetailModel>>() {}.type

                    var listOrders:ArrayList<OrderDetailModel>?= Gson().fromJson(jsonObject.getAsJsonObject("data").getAsJsonArray("users_order_data"),activeOrderType)

                    // activeOrderAdapter.setData(listActiveOrders!!)
                    listOrders?.let {
                        oRdersAdapter.setData(it)
                        if(listOrders.size>0)
                        {
                            binding.lnrEmpty.visibility=View.GONE
                        }
                        Log.d("list Orders","list Orders ${listOrders?.size}")
                    }


                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })
    }
}