package com.royalit.mfd.views.products

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
import com.royalit.mfd.models.ProductModel
import com.royalit.mfd.services.RetrofitClient
import com.royalit.mfd.views.cart.CardData
import com.bumptech.glide.Glide

class ProductListAdapter(lwn:LifecycleOwner): RecyclerView.Adapter<ProductListAdapter.OrderListViewHolder>() {
lateinit var prodcuList:ArrayList<ProductModel>


    var listCart=ArrayList<CartModel>()
    init {
        prodcuList=ArrayList<ProductModel>()
        CardData.cartListMap?.observe(lwn, Observer {

            listCart.clear()
            listCart.addAll(it)
            notifyDataSetChanged()
            Log.d("Observer data","Observer data ${it.size}")
        })
    }
    class OrderListViewHolder(item:View) :RecyclerView.ViewHolder(item){

        val txt_actual_price=item.findViewById<TextView>(R.id.txt_actual_price)
        val txt_net_price=item.findViewById<TextView>(R.id.txt_net_price)
        val txt_prodcut_name=item.findViewById<TextView>(R.id.txt_prodcut_name)
        val lnr_add_item=item.findViewById<View>(R.id.lnr_add_item)
        val txt_descriptiom=item.findViewById<TextView>(R.id.txt_descriptiom)
        val img_product=item.findViewById<ImageView>(R.id.img_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_order_item,parent,false)
        return OrderListViewHolder(view)
    }

    override fun getItemCount(): Int {
       return prodcuList.size
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {

        var productModel=prodcuList.get(position)
        holder.lnr_add_item.setOnClickListener {
            var cartModel=CartModel(

                1,
                productModel.name!!,
                productModel.discountPrice!!,
                productModel.id!!,
                productModel.image!!,

                )
            CardData.addCart(cartModel)
        }
        var isAdded=false;
        listCart.forEach {
            if(productModel.id==it.id)
            {
                isAdded=true
            }
        }
        if(isAdded)
        holder.lnr_add_item.visibility=View.INVISIBLE
        else
        holder.lnr_add_item.visibility=View.VISIBLE

        holder.txt_prodcut_name.text=productModel.name
        holder.txt_actual_price.text="₹ ${productModel.originalPrice}"
        holder.txt_net_price.text="₹ ${productModel.discountPrice}"
        holder.txt_descriptiom.text="${productModel.description}"
        Glide.with(holder.img_product.context)

            .load("${RetrofitClient.PRODUCT_IMAGE_PATH}/${productModel.image}")
            .placeholder(holder.img_product.context.getDrawable(R.drawable.img_wash))

            .into(holder.img_product);
        Log.d("Image path","image Path ${RetrofitClient.PRODUCT_IMAGE_PATH}/${productModel.image}")

    }

    fun setData(prodcuLists:List<ProductModel>)
    {
        prodcuList.clear()
        prodcuList.addAll(prodcuLists)
        notifyDataSetChanged()
    }
}