package com.example.basekotlin.ui.carrier_name

import androidx.core.widget.addTextChangedListener
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showToastById
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.databinding.ActivityCarrierNameBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.Utils


class CarrierNameActivity :
    BaseActivity<ActivityCarrierNameBinding>(ActivityCarrierNameBinding::inflate) {

    override fun initView() {
        binding.apply {
            edtName.setText(PrefManager.isShowCarrier)
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            tvApply.tap {
                Utils.hideKeyboard(this@CarrierNameActivity)
                if (edtName.text.trim().isEmpty()) {
                    showToastById(R.string.please_enter_something)
                } else {
                    PrefManager.isShowCarrier = edtName.text.trim().toString()
                    showToastById(R.string.carrier_name_success)
                }
            }

            ivCancel.tap {
                PrefManager.isShowCarrier = ""
                edtName.setText("")
                ivCancel.gone()
            }

            edtName.addTextChangedListener {
                if (edtName.text.trim().isEmpty()) {
                    ivCancel.gone()
                } else {
                    ivCancel.visible()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}