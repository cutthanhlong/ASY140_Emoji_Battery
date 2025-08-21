package com.example.basekotlin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.basekotlin.util.NetworkManager
import com.example.basekotlin.util.SharedPreUtils
import com.facebook.FacebookSdk


class MyApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        SharedPreUtils.init(this)

        FacebookSdk.sdkInitialize(applicationContext)

    }


}