package com.example.basekotlin.ui.about

import android.annotation.SuppressLint
import android.graphics.Paint
import com.example.basekotlin.BuildConfig
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityAboutBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.policy.PolicyActivity


class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.apply {
            tvVersion.text = getString(R.string.version) + " ${BuildConfig.VERSION_NAME}"
            tvPolicy.paintFlags = tvPolicy.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            tvPolicy.tap { startNextActivity(PolicyActivity::class.java, null) }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

}