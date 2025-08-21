package com.example.basekotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.basekotlin.ads.IsNetWork
import com.example.basekotlin.ui.welcome_back.WelcomeBackActivity
import com.example.basekotlin.util.NetworkManager
import com.example.basekotlin.util.SharedPreUtils
import com.facebook.FacebookSdk


class MyApplication : Application(), Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var isEnableWB = true

    }

    private var currentActivity: Activity? = null
    override fun onCreate() {
        super<Application>.onCreate()
        context = applicationContext
        SharedPreUtils.init(this)

        FacebookSdk.sdkInitialize(applicationContext)

        registerActivityLifecycleCallbacks(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (isEnableWB)
        currentActivity?.apply {
            if (IsNetWork.haveNetworkConnection(this)) {
                startActivity(Intent(this, WelcomeBackActivity::class.java))
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        isEnableWB = true
    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }


}