package com.example.basekotlin.ui.language

import android.widget.Toast
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.databinding.ActivityLanguageStartBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.intro.IntroActivity
import com.example.basekotlin.ui.language.adapter.LanguageAdapter
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.SystemUtil

class LanguageStartActivity :
    BaseActivity<ActivityLanguageStartBinding>(ActivityLanguageStartBinding::inflate) {

    private var codeLang: String? = null
    private var toast: Toast? = null

    override fun getData() {
        super.getData()

        codeLang = SystemUtil.getPreLanguage(this)
    }

    override fun initView() {
        binding.rcvLangStart.apply {
            adapter = LanguageAdapter {
                codeLang = it
                binding.ivRight.visible()
                binding.viewLottie.gone()

                SystemUtil.changeLang(this@LanguageStartActivity, codeLang)
                binding.tvTitle.text = getString(R.string.language)
                binding.tvContent.text = getString(R.string.please_select_a_language_to_continue)
            }.apply {
                addListData(InsertListManager.getListLanguage())
//                if (codeLang != "") {
//                    setCheck(codeLang)
//                }
            }
        }
    }

    override fun bindView() {
        binding.ivRight.tap {
            SystemUtil.saveLocale(this, codeLang)

            if (codeLang != "") {
                SystemUtil.saveLocale(this, codeLang)
                startNextActivity()
            } else {
                if (toast != null) toast!!.cancel()
                toast = Toast.makeText(
                    this, getString(R.string.please_choose_a_language), Toast.LENGTH_SHORT
                )
                toast!!.show()
            }
        }
    }

    private fun startNextActivity() {
        startNextActivity(IntroActivity::class.java, null)
        finish()
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

    override fun onBack() {
        finishAffinity()
    }
}
