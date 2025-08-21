package com.example.basekotlin.ui.splash

import androidx.lifecycle.lifecycleScope
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.databinding.ActivitySplashBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.language.LanguageStartActivity
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.SharedPreUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {


    override fun initView() {
        SharedPreUtils.getInstance().setCountOpenApp(this)

        lifecycleScope.launch {
            for (i in 1..100) {
                binding.pbLoading.progress = i
                binding.tvLoading.text = getString(R.string.loading, i)
                delay(30)
            }
            startNextActivity()
        }
    }

    private fun startNextActivity() {
        startNextActivity(LanguageStartActivity::class.java, null)
        finishAffinity()
    }

    override fun onBack() {}
}