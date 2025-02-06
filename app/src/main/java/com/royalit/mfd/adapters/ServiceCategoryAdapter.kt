package com.royalit.mfd.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.royalit.mfd.R
import com.royalit.mfd.models.Categories
import com.royalit.mfd.services.RetrofitClient.CATEGORY_IMAGE_PATH
import com.royalit.mfd.views.products.ProductListActivity
import com.bumptech.glide.Glide

class ServiceCategoryAdapter: RecyclerView.Adapter<ServiceCategoryAdapter.ServiceCategoryHolder>() {
    lateinit var listCatagories:ArrayList<Categories>
    init {
        listCatagories= ArrayList()
}

    class ServiceCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_service=itemView.findViewById<ImageView>(R.id.img_service)
        val txt_title=itemView.findViewById<TextView>(R.id.txt_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceCategoryHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_service_category,parent,false)
       return ServiceCategoryHolder(view)
    }

    override fun getItemCount(): Int {

        return listCatagories.size
    }

    override fun onBindViewHolder(holder: ServiceCategoryHolder, position: Int) {

        Log.d("Image path","image Path $CATEGORY_IMAGE_PATH/${listCatagories.get(position).image}")
        holder.txt_title.text=listCatagories.get(position).name
        Glide.with(holder.img_service.context)

            .load("$CATEGORY_IMAGE_PATH/${listCatagories.get(position).image}")
            .placeholder(holder.img_service.context.getDrawable(R.drawable.img_wash))

        .into(holder.img_service);

        holder.itemView.setOnClickListener {
            val intent= Intent(it.context, ProductListActivity::class.java)
            intent.putExtra("category_id",listCatagories.get(position).id)
            it.context.startActivity(intent)
        }
    }
    fun setData(listCatagori:List<Categories>)
    {
       // listCatagories.clear()
        listCatagories.clear()
        listCatagories.addAll(listCatagori)

       notifyDataSetChanged()

    }

}