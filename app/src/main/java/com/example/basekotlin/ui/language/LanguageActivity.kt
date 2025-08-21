package com.example.basekotlin.ui.language

import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityLanguageBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.language.adapter.LanguageAdapter
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SystemUtil

class LanguageActivity : BaseActivity<ActivityLanguageBinding>(ActivityLanguageBinding::inflate) {

    private var codeLang: String? = null

    override fun initView() {
        codeLang = SystemUtil.getPreLanguage(this)

        binding.apply {
            rcvLang.apply {
                adapter = LanguageAdapter { codeLang = it }.apply {
                    addListData(InsertListManager.getListLanguage())
                    setCheck(codeLang)
                }
            }
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivCheck.tap { onNextActivity() }
        }
    }

    private fun onNextActivity() {
        SystemUtil.saveLocale(this, codeLang)

        startNextActivity(MainActivity::class.java, null)
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

}
