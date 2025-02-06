package com.royalit.mfd.views.orders

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.OrderDetailModel

class ORdersAdapter: RecyclerView.Adapter<ORdersAdapter.OrderViewHolder>() {
lateinit var listOrder:ArrayList<OrderDetailModel>
        init {
            listOrder=ArrayList()
        }
    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txt_orderid=view.findViewById<TextView>(R.id.txt_orderid)
        val txt_date=view.findViewById<TextView>(R.id.txt_date)
        val txt_payment_option=view.findViewById<TextView>(R.id.txt_payment_option)
        val txt_payable_amount=view.findViewById<TextView>(R.id.txt_payable_amount)
        val txt_order_status=view.findViewById<TextView>(R.id.txt_order_status)
        val txt_payment_status=view.findViewById<TextView>(R.id.txt_payment_status)
        val txt_address=view.findViewById<TextView>(R.id.txt_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_order_items,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listOrder.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.txt_orderid.text=listOrder.get(position).orderId
        holder.txt_date.text=listOrder.get(position).orderedDate
        holder.txt_payment_option.text=listOrder.get(position).orderedDate
        holder.txt_payable_amount.text=listOrder.get(position).total
        holder.txt_order_status.text=listOrder.get(position).status
        holder.txt_payment_status.text=listOrder.get(position).paymentStatus
        var addressDetails=listOrder.get(position).addressDetails
        if (addressDetails != null) {
            holder.txt_address.text="${addressDetails.houseNumber}, " +
                    "${addressDetails.landmark}, " +
                    "${addressDetails.city}, " +
                    "${addressDetails.state}, " +
                    "${addressDetails.country}, "

        }
        holder.itemView.setOnClickListener {
            var intent=Intent(holder.itemView.context,OrderDetailsActivity::class.java)
            intent.putExtra("orderid",listOrder.get(position).orderId)
            it.context.startActivity(intent)
        }

    }
    fun setData(listOrders:ArrayList<OrderDetailModel>)
    {
        listOrder.clear()
        listOrder.addAll(listOrders)
        notifyDataSetChanged()
    }
}