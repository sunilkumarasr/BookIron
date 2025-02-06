package com.royalit.mfd.models

class CartModel (var quantity:Int, var productName:String, var price:String, var id:String, var image:String){

    override fun toString(): String {
        var jsonObj=HashMap<String,String>()
        jsonObj.put("id",id)
        jsonObj.put("price",price)
        jsonObj.put("quantity",quantity.toString())
        return jsonObj.toString()
    }
}