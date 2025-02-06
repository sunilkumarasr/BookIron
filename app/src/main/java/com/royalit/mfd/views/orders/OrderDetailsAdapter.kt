package com.royalit.mfd.views.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.OrderChild
import com.royalit.mfd.models.OrderParent


class OrderDetailsAdapter(val mContext: Context, val list: MutableList<OrderParent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

   init {
       val orderParent: Array<String> = arrayOf("Wash & Fold", "Wash & Iron", "Wash & Fold", "Wash & Iron")
       val OrderChildData1: MutableList<OrderChild> = mutableListOf(OrderChild("2 x  Tshirt  (Men)","6"),OrderChild("3 x  Jean  (Men)","22"))
       val OrderChildData2: MutableList<OrderChild> = mutableListOf(OrderChild("2 x  Tshirt  (Men)","6"), OrderChild("3 x  Jean  (Men)","6"))
       val OrderChildData3: MutableList<OrderChild> = mutableListOf(OrderChild("2 x  Tshirt  (Men)","18"),OrderChild("3 x  Jean  (Men)","19"))

       //val list : MutableList<OrderParent> = ArrayList()
       list.clear()
       val parentObj1 = OrderParent(title = orderParent[0], isExpanded = true, 0,subList = OrderChildData1)
       val parentObj2 = OrderParent(title = orderParent[1], isExpanded = true,0,subList = OrderChildData2)
       val parentObj3 = OrderParent(title = orderParent[2],isExpanded = true,0,OrderChildData1)
       val parentObj4 = OrderParent(title = orderParent[1], isExpanded = true,0,subList = OrderChildData3)
       list.add(parentObj1)
       list.add(parentObj2)
       list.add(parentObj3)
       list.add(parentObj4)
   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


            val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_parent_order, parent,false)
       return     GroupViewHolder(rowView)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val dataList = list[position]
            holder as GroupViewHolder
        val orderDetailsChildAdapter=OrderDetailsChildAdapter(mContext,dataList.subList)


            holder.apply {
                parentTV?.text = dataList.title
                downIV?.setOnClickListener {
                    dataList.isExpanded=! dataList.isExpanded
                    notifyDataSetChanged()
                }
                recycler_child!!.layoutManager=LinearLayoutManager(mContext)
                recycler_child!!.adapter=orderDetailsChildAdapter
                if( dataList.isExpanded)
                    recycler_child!!.visibility=View.VISIBLE
                else
                    recycler_child!!.visibility=View.GONE

                if(dataList.isExpanded)
                    downIV!!.rotation=270F;
                else
                    downIV!!.rotation=90F;
            }


    }


    }



    class GroupViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val parentTV = row.findViewById(R.id.txt_order_parent_item_name) as TextView?
        val downIV  = row.findViewById(R.id.img_arrow_down) as ImageView?
        val recycler_child  = row.findViewById(R.id.recycler_child) as RecyclerView?
    }




class OrderDetailsChildAdapter(var mContext: Context, val list: List<OrderChild>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_child_order, parent,false)
        return     ChildViewHolder(rowView)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataList = list[position]
        holder as ChildViewHolder
        holder.apply {
            childTV?.text = dataList.title
            txt_order_child_item_price?.text = "₹${dataList.price}"

        }

    }


    class ChildViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val childTV = row.findViewById(R.id.txt_order_child_item_name) as TextView?
        val txt_order_child_item_price = row.findViewById(R.id.txt_order_child_item_price) as TextView?

    }
}