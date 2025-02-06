package com.royalit.mfd.views.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.royalit.mfd.R

class ContactUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        val lnr_back=findViewById<View>(R.id.lnr_back)
        lnr_back.setOnClickListener {
            onBackPressed()
        }
    }
}