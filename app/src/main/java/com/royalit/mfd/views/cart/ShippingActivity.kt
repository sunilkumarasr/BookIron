package com.royalit.mfd.views.cart

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.royalit.mfd.R
import com.royalit.mfd.customviews.CustomDialog
import com.royalit.mfd.databinding.ActivityShippingBinding
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar



class ShippingActivity : AppCompatActivity() {
    lateinit var binding: ActivityShippingBinding
    var startDate="";
    var endDate="";
    var startTime="";
    var endTime="";
    var startDateTime:Long=0
    var dateformat=SimpleDateFormat("E, dd MMM")
    var dateformat2=SimpleDateFormat("yyyy-MM-dd")
    var timeformat=SimpleDateFormat("hh:mm")
    var timeformat2=SimpleDateFormat("hh:mm a")
    var startCalendar:Calendar=Calendar.getInstance()
    var endCalendar:Calendar=Calendar.getInstance()
    var paymentType=-1
    var hno=""
    var landmark=""
    var city=""
    var state=""
    var country=""
    var pincode=""
    var addressid=""
    var subtotal=0.0
    var total=0.0
    var tax=0.0
    lateinit var product_array:JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddAddress.setOnClickListener {
            var intent=Intent(applicationContext,AddressListActivity::class.java)
                intent.putExtra("showButton",true)
            startActivityForResult(intent,200)
        }
        binding.radioGrp.setOnCheckedChangeListener(object:OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if(p1==R.id.radio_one)
                {
                    paymentType=2;
                }else if(p1==R.id.radio_cash)
                {
                    paymentType=1;
                }
            }

        })
        var gson=Gson()
        product_array= JSONArray()
        CardData.cartListMap.value?.forEach {

            var json=java.util.HashMap<String,Any>()
            json.put("id",it.id)
            json.put("price",it.price)
            json.put("quantity",it.quantity)
            product_array.put(json.toString())
            subtotal=subtotal+(it.price.toDouble()*it.quantity)
            tax=0.0

        }
        total=subtotal+tax
        binding.txtCostNo.text="₹ ${total}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==200&&resultCode==200)
        {
            hno=data?.getStringExtra("hno").toString()
            landmark=data?.getStringExtra("landmark").toString()
            city=data?.getStringExtra("city").toString()
            state=data?.getStringExtra("state").toString()
            country=data?.getStringExtra("country").toString()
            pincode=data?.getStringExtra("pincode").toString()
            addressid=data?.getStringExtra("id").toString()
            binding.txtSelectedAddress.text="$hno, $landmark, $city, $state, $country, $pincode"
        }
    }

    fun paynow(view: View) {

        Log.d("product_array","product_array $product_array")
        if(startDate.isEmpty()||startTime.isEmpty())
        {
            Utils.showMessage("Select Pickup date",applicationContext)
            return
        }
        if(endDate.isEmpty()||endTime.isEmpty())
        {
            Utils.showMessage("Select Delivery date",applicationContext)
            return
        }
        if(addressid.isEmpty())
        {
            Utils.showMessage("Please add address",applicationContext)

            return
        }
        val userdataStr= MyPref.getUser(applicationContext)

        val jsobObj= JSONObject(userdataStr);

        //  val email=jsobObj.getString("email");
        val userId=jsobObj.getString("user_id");
        val request=HashMap<String,Any>()
        request.put("user_id",userId)
        request.put("address_id",addressid)
        request.put("card_id","0")
        request.put("payment_method",paymentType.toString())
        request.put("sub_total",subtotal.toString())
        request.put("tax",tax.toString())
        request.put("total",total.toString())
        request.put("expected_pickup_date",startDate+" "+startTime)
        request.put("expected_delivery_date",endDate+" "+endTime)

        product_array= JSONArray()
       var product_array2= JSONArray()

        CardData.cartListMap.value?.forEach {
            var jobj=HashMap<String,String>()
            jobj.put("price",it.price)
            jobj.put("id",it.id)
            jobj.put("quantity",it.quantity.toString())

            product_array.put(jobj)

            Log.e("JSONOBJ ","JSON OBJ ${jobj.toString()}")
            Log.e("JSONOBJ ","JSON OBJ ${product_array.toString()}")

            product_array2.put(jobj.toString())
            Log.e("JSONOBJ ","JSON OBJ ${product_array2.toString()}")

        }

       var s=  Gson().toJsonTree(CardData.cartListMap.value).getAsJsonArray()
       // val jsonArray: JSONArray = JSONArray(s)

        request.put("product_data", s)
        Log.d("Place Order Data","Please Order Data ${request.toString()}")

        if(paymentType==1)
        {
           // startActivity(Intent(applicationContext,PlaceOrderActivity::class.java))
                placeOrder(request)
        }
        else if(paymentType==2)
            Utils.showMessage("Payment Gateway Integration",applicationContext)
        else
            Utils.showMessage("Choose Payment Type",applicationContext)

    }

    private fun placeOrder(hashMap:Any) {
        val customDialog= CustomDialog(this@ShippingActivity);
        customDialog.showDialog(this@ShippingActivity,true)
        var request= RetrofitClient.apiInterface.placeOrder(hashMap,"E28DA37796A786FAB9DC6427B1D19" )
        request.enqueue(object : Callback<Any> {




            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                customDialog.closeDialog()

                var strRes= response.body();
                if(strRes==null)
                {
                    return
                }
                //strRes=strRes.replace("!!","")
                Log.d("strRes ",response.message());
                Log.d("strRes ",response.code().toString());
                Log.d("strRes ",strRes.toString());

                Log.d("strRes ","Calling api 2");
                val jsonObject: JsonObject? = strRes?.let { Utils.getJsonObject(it) }


              //  Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)
                if(jsonObject!!.get("status").asString=="200")
                {

                   // val activeOrderType: Type = object : TypeToken<List<AddressModel>>() {}.type


                    Utils.showMessage(jsonObject!!.get("message").asString,applicationContext)

                    var listAddress= jsonObject.getAsJsonObject("data").getAsJsonObject("order_data").get("reference_id").asString

                    // activeOrderAdapter.setData(listActiveOrders!!)
                    listAddress.let {
                        Log.d("Order Details Data","Order Details Data ${listAddress}")
                      var intent=  Intent(applicationContext,PlaceOrderActivity::class.java)
                        intent.putExtra("orderid",listAddress)
                        startActivity(intent)

                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("strRes ","Calling api 3");
                customDialog.closeDialog()
            }

        })

    }

    fun pickuptime(view: View) {
        pickDateTime(0)
    }
    fun droptime(view: View) {
        if(startDate.isEmpty())
        {
            Utils.showMessage("Please set pickup time",applicationContext)
            return
        }
        pickDateTime(1)
    }
    fun back(view: View) {
        finish()
    }
    private fun pickDateTime(which:Int) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

      val datePickerDialog=  DatePickerDialog(this@ShippingActivity, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(this@ShippingActivity, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month+1, day, hour, minute)

                if(which==0) {

                    var monthsdate="${month+1}"
                    if(monthsdate.length==1)
                        monthsdate="0$monthsdate"

                    var daydate="$day"
                    if(daydate.length==1)
                        daydate="0$daydate"


                    startDate="$year-$monthsdate-$daydate"
                    startTime="$hour:$minute"
                    binding.inputPickip.setText(dateformat.format(dateformat2.parse(startDate))+"\n"+timeformat2.format(timeformat.parse(startTime)))


                    startCalendar.set(year,month+1,day,hour,minute)
                    Log.d("date start","$startDate $startTime")
                    Log.d("date start",dateformat.format( dateformat2.parse(startDate))+"\n"+timeformat2.format(timeformat.parse(startTime)))
                }
                else{

                    endCalendar.set(year,month+1,day,hour,minute)
                    if(startCalendar.timeInMillis>endCalendar.timeInMillis)
                    {
                        Utils.showMessage("Drop time should be after pickup time",applicationContext)
                        return@OnTimeSetListener
                    }
                    var monthsdate="${month+1}"
                    if(monthsdate.length==1)
                        monthsdate="0$monthsdate"

                    var daydate="$day"
                    if(daydate.length==1)
                        daydate="0$daydate"


                    endDate="$year-${monthsdate}-$daydate"
                    endTime="$hour:$minute"
                    Log.d("date end","$endDate $endTime")

                    //  Log.d("date",dateformat.format( dateformat2.parse("$year-$month-$day $hour:$minute")))
                binding.inputDrop.setText(dateformat.format( dateformat2.parse(endDate))+"\n"+timeformat2.format(timeformat.parse(endTime)))
                }



            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay)
        datePickerDialog.datePicker.minDate=Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }
}