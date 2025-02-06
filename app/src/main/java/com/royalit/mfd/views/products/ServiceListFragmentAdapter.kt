package com.royalit.mfd.views.products

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ServiceListFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return  10;
    }

    override fun getItem(position: Int): Fragment {
        val serviceListFragment=ServiceListFragment()
        return  serviceListFragment;
    }

}