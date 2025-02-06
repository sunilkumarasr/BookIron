package com.royalit.mfd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.royalit.mfd.R


class ViewPagerAdapter(): PagerAdapter() {
    val layouts= mutableListOf<Int>()
    init {
        layouts.add( R.layout.layout_intro_screen_one);
        layouts.add( R.layout.layout_intro_screen);
        layouts.add( R.layout.layout_intro_screen_three);
    }
    override fun getCount(): Int {

        return  3
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
       var layoutInflater = LayoutInflater.from(container.context)
        val view: View = layoutInflater.inflate(layouts.get(position), container, false)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val view = `object` as View
        container.removeView(view)
       // super.destroyItem(container, position, `object`)
    }


}