package com.example.basekotlin.ui.setting

import com.example.basekotlin.MyApplication
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivitySettingsBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.about.AboutActivity
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.ui.language.LanguageActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SharedPreUtils
import com.example.basekotlin.util.SystemUtil
import com.example.basekotlin.util.rateApp
import com.example.basekotlin.util.shareApp

class SettingActivity : BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {


    override fun getData() {
        val codeLang = SystemUtil.getPreLanguage(this)
        binding.tvLang.text =
            InsertListManager.getListLanguage().find { it.code == codeLang }?.name ?: ""

        if (SharedPreUtils.getInstance().isRated(this)) {
            binding.btnRateUs.gone()
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            btnLanguage.tap { startNextActivity(LanguageActivity::class.java, null) }

            btnShare.tap {
                MyApplication.isEnableWB = false
                shareApp()
            }

            btnRateUs.tap {
                MyApplication.isEnableWB = false
                rateApp(btnRateUs)
            }

            btnAbout.tap { startNextActivity(AboutActivity::class.java, null) }

            btnHowToUse.tap { startNextActivity(HowToUseActivity::class.java, null) }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

}