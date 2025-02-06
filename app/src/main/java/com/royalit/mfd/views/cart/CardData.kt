package com.royalit.mfd.views.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.royalit.mfd.models.CartModel


object CardData {
    var cartListMap: MutableLiveData<ArrayList<CartModel>> = MutableLiveData<ArrayList<CartModel>>()


    fun addCart(productModel: CartModel){



        productModel.id?.let {
            Log.d("Cart Size","Cart Size11 ${cartListMap.value?.size}")


            Log.d("Cart Size","Cart Size22 ${cartListMap.value?.size}")

            if(cartListMap==null)
                cartListMap=MutableLiveData()
            if(cartListMap.value==null)
                cartListMap.value= ArrayList()

        var list=cartListMap?.value
            var isexist=false;
            cartListMap?.value?.forEach {
                    if(it.id==productModel.id){
                        it.quantity=it.quantity+1
                        isexist=true
                    }
            }
            if(!isexist) {
                Log.d("Cart Size","Cart Size33 ${cartListMap.value?.size}")

                var cartModel=CartModel(
                    1,
                    productModel.productName!!,
                    productModel.price!!,
                    productModel.id!!,
                    productModel.image!!,
                )
                cartListMap?.value?.add(cartModel)
                Log.d("Cart Size","Cart Size44 ${cartListMap.value?.size}")

            }else
            {
                cartListMap?.value?.forEach {
                    Log.d("Cart Size","Cart Size66 ${it.quantity}")

                }
            }
        }

        Log.d("Cart Size","Cart Size ${cartListMap.value?.size}")
        cartListMap.postValue(
            cartListMap.value
        )
    }
    fun remove(productModel: CartModel)
    {
        productModel.id?.let {

            var isexist=false;
            var cartModel: CartModel? =null;
            cartListMap?.value?.forEach {
                if(it.id==productModel.id){
                    if(it.quantity==1)
                    {
                        cartModel=it;
                        isexist=true
                    }
                    it.quantity=it.quantity-1

                }
            }
            if(isexist&&cartModel!=null) {
                cartListMap?.value?.remove(cartModel)
            }


        }
        cartListMap.postValue(
            cartListMap.value
        )

    }
/*
    var cartListMap: MutableLiveData<HashMap<String,CartModel>>? = null

    fun getCartData(): MutableLiveData<HashMap<String,CartModel>>? {
        if (cartListMap == null) {
            cartListMap = MutableLiveData<HashMap<String,CartModel>>()
            cartListMap?.value=HashMap()

        }
        return cartListMap
    }
    fun addCart(productModel: ProductModel){

        productModel.id?.let {
            getCartData()

            if(cartListMap?.value?.contains(it)!!)
            {
                var cartModel= cartListMap?.value?.get(it)

                    cartModel?.quantity=cartModel?.quantity!!+1
                    cartListMap?.value?.putIfAbsent(it,cartModel)

                Log.d("Observer data","Observer data 22 ${it} ${cartListMap?.value?.size}")


            }else
            {
                var cartModel=CartModel(
                    1,
                    productModel.name!!,
                    productModel.discountPrice!!,
                    productModel.id!!,
                    )
                cartListMap?.value?.putIfAbsent(it,cartModel)
                Log.d("Observer data","Observer data 11 ${it} ${cartListMap?.value?.size}")

            }


        }
    }
    fun remove(productModel: ProductModel)
    {
        productModel.id?.let {
            if(cartListMap?.value?.contains(it)!!)
            {
                var cartModel= cartListMap?.value?.get(it)
                if(cartModel?.quantity==1)
                {
                    cartListMap?.value?.remove(it)
                }else
                {
                    cartModel?.quantity=cartModel?.quantity!!-1
                    cartListMap?.value?.putIfAbsent(it,cartModel)
                }


            }


        }

    }*/


}