package com.example.basekotlin.ui.wifi

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityWifiBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class WifiActivity : BaseActivity<ActivityWifiBinding>(ActivityWifiBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun initView() {
        colorPickerDialog = ColorPickerDialog(this, onClick = {
            PrefManager.wifiColor = it
            binding.ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.wifiColor.toColorInt())
        })

        binding.apply {
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.wifiColor.toColorInt())

            sbWifi.progress = PrefManager.wifiSize
            tvWifi.text = getString(R.string.wifi_s, "${PrefManager.wifiSize}")
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            sbWifi.onProgressChanged { progress, _ ->
                tvWifi.text = getString(R.string.wifi_s, "$progress")
                PrefManager.wifiSize = progress
            }

            btnColor.tap {
                val color = fromColor(PrefManager.wifiColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}