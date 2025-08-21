package com.example.basekotlin.ui.no_internet

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.provider.Settings
import com.example.basekotlin.MyApplication
import com.example.basekotlin.ads.IsNetWork
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityNoInternetBinding
import com.example.basekotlin.model.EnumStatusColor

class NoInternetActivity :
    BaseActivity<ActivityNoInternetBinding>(ActivityNoInternetBinding::inflate) {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun initView() {
        super.initView()
        MyApplication.isEnableWB = false

        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onBack()
            }
        }
    }

    override fun bindView() {
        super.bindView()

        binding.tvTryAgain.tap {
            if (IsNetWork.haveNetworkConnection(this)) {
                onBack()
            } else {
                MyApplication.isEnableWB = false
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }
}