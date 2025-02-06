package com.royalit.mfd.customviews


import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.core.content.res.ResourcesCompat
import com.royalit.mfd.R


class CustomTextView: androidx.appcompat.widget.AppCompatTextView {
    init {
        //this.setTextSize(26.0F)

        val typeface = ResourcesCompat.getFont(context, R.font.montserrat)
        //this.setTypeface(typeface)
       // this.setTextColor(resources.getColor(R.color.black))
    }

    constructor(context: Context?) : super(context!!) // constructor
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context!!,attrs) {} // constructor



}