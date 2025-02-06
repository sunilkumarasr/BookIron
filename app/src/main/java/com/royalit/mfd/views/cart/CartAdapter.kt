package com.royalit.mfd.views.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.CartModel
import com.royalit.mfd.services.RetrofitClient
import com.bumptech.glide.Glide

class CartAdapter(lwn:LifecycleOwner) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

     var listCart=ArrayList<CartModel>()
    init {
        CardData.cartListMap?.observe(lwn, Observer {

            listCart.clear()
            listCart.addAll(it)
            notifyDataSetChanged()
            Log.d("Observer data","Observer data ${it.size}")
        })
    }
    class CartViewHolder(item: View): RecyclerView.ViewHolder(item) {
                    val txt_prodcut_name=item.findViewById<TextView>(R.id.txt_prodcut_name)
                    val txt_net_price=item.findViewById<TextView>(R.id.txt_net_price)
                    val txt_item_count=item.findViewById<TextView>(R.id.txt_item_count)
                    val lnr_minus_item=item.findViewById<View>(R.id.lnr_minus_item)
                    val lnr_add_item=item.findViewById<View>(R.id.lnr_add_item)
                    val img_product=item.findViewById<ImageView>(R.id.img_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  listCart.size!!
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            var cartModel=listCart.get(position)
        holder.txt_prodcut_name.text=cartModel?.productName
        holder.txt_item_count.text=cartModel?.quantity.toString()
        holder.txt_net_price.text="₹ ${cartModel?.price.toString()}"
        holder.lnr_add_item.setOnClickListener {
            if (cartModel != null) {
                CardData.addCart(cartModel)
            }
        }
        Glide.with(holder.img_product.context)

            .load("${RetrofitClient.PRODUCT_IMAGE_PATH}/${cartModel.image}")
            .placeholder(holder.img_product.context.getDrawable(R.drawable.img_wash))

            .into(holder.img_product);
        holder.lnr_minus_item.setOnClickListener {
            if (cartModel != null) {
                CardData.remove(cartModel)
            }
        }

    }
}