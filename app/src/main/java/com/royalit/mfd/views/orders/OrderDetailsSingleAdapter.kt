package com.royalit.mfd.views.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.Products


class OrderDetailsSingleAdapter(val mContext: Context) : RecyclerView.Adapter<OrderDetailsSingleAdapter.PorductViewHolder>() {
    lateinit var list: ArrayList<Products>

    init {

        list = ArrayList<Products>()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PorductViewHolder {


        val rowView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_child_order, parent, false)
        return PorductViewHolder(rowView)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PorductViewHolder, position: Int) {

        holder.parentitemName?.text  ="${list.get(position).quantity} X ${list.get(position).productName}"
        holder.parentitemPrice?.text  =list.get(position).price
    }


    class PorductViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val parentitemName = row.findViewById(R.id.txt_order_child_item_name) as TextView?
        val parentitemPrice = row.findViewById(R.id.txt_order_child_item_price) as TextView?

    }
    fun setData(lists: List<Products>){
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()

}
}



