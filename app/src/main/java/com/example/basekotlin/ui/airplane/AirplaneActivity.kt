package com.example.basekotlin.ui.airplane

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityAirplaneBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class AirplaneActivity : BaseActivity<ActivityAirplaneBinding>(ActivityAirplaneBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun initView() {
        colorPickerDialog = ColorPickerDialog(this, onClick = {
            PrefManager.airplaneColor = it
            binding.ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.airplaneColor.toColorInt())
        })

        binding.apply {
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.airplaneColor.toColorInt())

            sbAirplane.progress = PrefManager.airplaneSize
            tvAirplane.text = getString(R.string.airplane_s, "${PrefManager.airplaneSize}")
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            sbAirplane.onProgressChanged { progress, _ ->
                tvAirplane.text = getString(R.string.airplane_s, "$progress")
                PrefManager.airplaneSize = progress
            }

            btnColor.tap {
                val color = fromColor(PrefManager.airplaneColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}