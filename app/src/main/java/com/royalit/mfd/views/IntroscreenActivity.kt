package com.royalit.mfd.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.royalit.mfd.R
import com.royalit.mfd.adapters.ViewPagerAdapter
import com.royalit.mfd.utils.MyPref
import com.royalit.mfd.views.home.DashboardActivity

class IntroscreenActivity : AppCompatActivity() {
    lateinit var viewPager:ViewPager
    lateinit var radio_one:RadioButton
    lateinit var radio_two:RadioButton
    lateinit var radio_three:RadioButton
    lateinit var viewpagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userdata= MyPref.getUser(applicationContext)

        if(userdata!=null&&userdata.length>10)
        {
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_main)
        viewPager=findViewById<ViewPager>(R.id.viewPager)
        radio_one=findViewById<RadioButton>(R.id.radio_one)
        radio_two=findViewById<RadioButton>(R.id.radio_two)
        radio_three=findViewById<RadioButton>(R.id.radio_tree)
        viewpagerAdapter= ViewPagerAdapter()

        viewPager.adapter=viewpagerAdapter
        viewPager.addOnPageChangeListener(object :OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when(position)
                {
                    0->radio_one.isChecked=true
                    1->radio_two.isChecked=true
                    2->radio_three.isChecked=true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    fun skip(view: View) {
        startActivity(Intent(applicationContext,LoginActivity::class.java))
        finish()
    }
    fun next(view: View) {
       if(viewPager.currentItem<2)
       {
           viewPager.currentItem=viewPager.currentItem+1
       }else
       {
           startActivity(Intent(applicationContext,LoginActivity::class.java))
           finish()
       }
    }
}