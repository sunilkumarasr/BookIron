package com.royalit.mfd.utils

import android.content.Context

class MyPref() {
    companion object{

        fun setUser(ctx:Context,user:String)
        {
            val sharedPreference =  ctx.getSharedPreferences("MyBookIron", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
            editor.putString("user",user)
            editor.commit()

        }
        fun getUser(ctx:Context): String?
        {
            val sharedPreference =  ctx.getSharedPreferences("MyBookIron", Context.MODE_PRIVATE)
            return sharedPreference.getString("user","")
        }

        fun clear(ctx: Context) {
            val sharedPreference =  ctx.getSharedPreferences("MyBookIron", Context.MODE_PRIVATE)
            sharedPreference.edit().clear().commit()

        }
    }
}