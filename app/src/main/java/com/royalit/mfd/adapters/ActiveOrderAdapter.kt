package com.royalit.mfd.adapters

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.ActiveOrder
import com.royalit.mfd.utils.Utils
import com.royalit.mfd.views.orders.OrderDetailsActivity
import java.text.SimpleDateFormat

class ActiveOrderAdapter(val res:Resources): RecyclerView.Adapter<ActiveOrderAdapter.ActiveORderHolder>() {
    lateinit var listCatagories:ArrayList<ActiveOrder>
    val firstApiFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val firstApiFormat2 = SimpleDateFormat("EEE, dd MMM")

    init {
        listCatagories= ArrayList()
        Log.d("Date formar with day ",firstApiFormat2.format( firstApiFormat.parse("2023-07-10 12:51:59")));

    }
    class ActiveORderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_order=itemView.findViewById<TextView>(R.id.txt_order)
        val txt_order_status=itemView.findViewById<TextView>(R.id.txt_order_status)
        val txt_start_time=itemView.findViewById<TextView>(R.id.txt_start_time)
        val txt_end_time=itemView.findViewById<TextView>(R.id.txt_end_time)
        val txt_start_date=itemView.findViewById<TextView>(R.id.txt_start_date)
        val txt_end_date=itemView.findViewById<TextView>(R.id.txt_end_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveORderHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_active_order,parent,false)
       return ActiveORderHolder(view)
    }

    override fun getItemCount(): Int {

        return listCatagories.size
    }

    override fun onBindViewHolder(holder: ActiveORderHolder, position: Int) {
        holder.txt_order.text="Order #${listCatagories.get(position).orderId}"
        if(listCatagories.get(position).statusId=="3")
            holder.txt_order_status.background=res.getDrawable(R.drawable.rectangle_order_picked)
        else if(listCatagories.get(position).statusId=="4")
            holder.txt_order_status.background=res.getDrawable(R.drawable.rectangle_order_confirmed)
        else
            holder.txt_order_status.background=res.getDrawable(R.drawable.rectangle_order_completed)

        holder.txt_order_status.text="${listCatagories.get(position).status}"

        if(listCatagories.get(position).expectedPickupDate!=null&& listCatagories.get(position).expectedPickupDate!!.isNotEmpty())
        {
            holder.txt_start_date.text= Utils.getDateInMonthFormat(listCatagories.get(position).expectedPickupDate!!)//holder.txt_start_date.text="NA";
            holder.txt_start_time.text=Utils.getDateInTimeFormat(listCatagories.get(position).expectedPickupDate!!);//holder.txt_start_time.text="NA";

        }else
        {
            holder.txt_start_date.text= "NA"//holder.txt_start_date.text="NA";
            holder.txt_start_time.text="NA";//holder.txt_start_time.text="NA";
        }
        if(listCatagories.get(position).expectedDeliveryDate!=null&& listCatagories.get(position).expectedDeliveryDate!!.isNotEmpty())
        {
            holder.txt_end_time.text=Utils.getDateInMonthFormat(listCatagories.get(position).expectedDeliveryDate!!);
            holder.txt_end_date.text=Utils.getDateInTimeFormat(listCatagories.get(position).expectedDeliveryDate!!);
        }else
        {
            holder.txt_end_time.text="NA";
            holder.txt_end_date.text="NA";
        }
        holder.itemView.setOnClickListener {
            var intent=Intent(holder.itemView.context,OrderDetailsActivity::class.java)
            intent.putExtra("orderid",listCatagories.get(position).orderId)
            holder.itemView.context.startActivity(intent)
        }
    }
    fun setData(listCatagori:List<ActiveOrder>)
    {
        // listCatagories.clear()
        listCatagories.clear()
        listCatagories.addAll(listCatagori)

        notifyDataSetChanged()

    }
}