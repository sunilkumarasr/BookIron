package com.royalit.mfd.models

data class OrderParent(val title:String, var isExpanded:Boolean, var type:Int, var subList:List<OrderChild>) {

}

object Constants {
    const val PARENT = 0
    const val CHILD = 1
}