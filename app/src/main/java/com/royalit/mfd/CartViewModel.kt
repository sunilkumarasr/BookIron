package com.royalit.mfd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.royalit.mfd.models.CartModel

class CartViewModel:ViewModel() {
    var cartListMap: MutableLiveData<HashMap<String, CartModel>>? = null

    companion object {
        private var instance : CartViewModel? = null
        fun getInstance() =
            instance ?: synchronized(CartViewModel::class.java){
                instance ?: CartViewModel().also { instance = it }
            }
    }
}