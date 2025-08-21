package com.example.basekotlin.ui.notch

import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityNotchBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.ui.notch.adapter.NotchAdapter
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.PrefManager

class NotchActivity : BaseActivity<ActivityNotchBinding>(ActivityNotchBinding::inflate) {

    override fun initView() {
        super.initView()

        binding.rcvData.apply {
            setHasFixedSize(true)
            adapter = NotchAdapter {
                PrefManager.isShowNotch = it.id
            }.apply {
                addListData(InsertListManager.getListNotch())
                setCheck(PrefManager.isShowNotch)
            }
        }
    }

    override fun bindView() {
        super.bindView()
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}