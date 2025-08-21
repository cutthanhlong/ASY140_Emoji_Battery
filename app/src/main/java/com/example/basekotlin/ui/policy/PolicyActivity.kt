package com.example.basekotlin.ui.policy

import android.annotation.SuppressLint
import com.example.basekotlin.R
import com.example.basekotlin.ads.IsNetWork
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.inVisible
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.databinding.ActivityPolicyBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.util.SettingManager

class PolicyActivity : BaseActivity<ActivityPolicyBinding>(ActivityPolicyBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        binding.apply {
            if (SettingManager.linkPolicy != "" && IsNetWork.haveNetworkConnection(this@PolicyActivity)) {
                webView.visible()
                lnNoInternet.gone()

                webView.settings.javaScriptEnabled = true
                webView.loadUrl(SettingManager.linkPolicy)
            } else {
                webView.gone()
                lnNoInternet.visible()
            }
        }
    }

    override fun bindView() {
        binding.ivLeft.tap { onBack() }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

}