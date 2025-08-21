package com.example.basekotlin.ui.charge

import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityChargeBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.charge.adapter.ChargeAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.PrefManager


class ChargeActivity : BaseActivity<ActivityChargeBinding>(ActivityChargeBinding::inflate) {

    override fun initView() {
        binding.apply {
            rcvData.apply {
                setHasFixedSize(true)
                adapter = ChargeAdapter {
                    PrefManager.batteryChargeIcon = it.id
                }.apply {
                    addListData(InsertListManager.getListCharge())
                    setCheck(PrefManager.batteryChargeIcon)
                }
            }
        }
    }

    override fun bindView() {
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