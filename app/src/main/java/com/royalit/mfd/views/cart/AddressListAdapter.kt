package com.royalit.mfd.views.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.AddressModel

class AddressListAdapter(val listActivity: AddressListActivity, val showButton: Boolean): RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>() {

    lateinit var listAddress:ArrayList<AddressModel>

    init {

        listAddress=ArrayList()
    }
    class AddressListViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txt_address=view.findViewById<CheckedTextView>(R.id.txt_address)
        val radio=view.findViewById<RadioButton>(R.id.radio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder {
        return AddressListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_address_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return  listAddress.size
    }

    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        val addressModel=listAddress.get(position)
        holder.txt_address.text=addressModel.houseNumber+","+
                addressModel.landmark+","+
                addressModel.city+","+
                addressModel.state+","+
                addressModel.country+","+
                addressModel.pincode
        holder.radio.isChecked=addressModel.isChecked
        if(showButton)
            holder.radio.visibility=View.VISIBLE
        else
            holder.radio.visibility=View.INVISIBLE

       holder.itemView.setOnClickListener {
           listAddress.forEach {
               it.isChecked=false;
               if (addressModel.id==it.id)
                   it.isChecked=true

           }
           notifyDataSetChanged()

           listActivity.selectedAddress(addressModel)
       }
    }

    fun setData(listAddresss: List<AddressModel>?)
    {
        listAddress.clear()
        listAddresss?.let { listAddress.addAll(it) }
        notifyDataSetChanged()
    }
}