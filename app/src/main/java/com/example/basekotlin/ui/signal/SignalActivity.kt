package com.example.basekotlin.ui.signal

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivitySignalBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class SignalActivity : BaseActivity<ActivitySignalBinding>(ActivitySignalBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun initView() {
        colorPickerDialog = ColorPickerDialog(this, onClick = {
            PrefManager.signalColor = it
            binding.ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.signalColor.toColorInt())
        })

        binding.apply {
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.signalColor.toColorInt())

            sbSignal.progress = PrefManager.signalSize
            tvSignal.text = getString(R.string.signal_s, "${PrefManager.signalSize}")
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            sbSignal.onProgressChanged { progress, _ ->
                tvSignal.text = getString(R.string.signal_s, "$progress")
                PrefManager.signalSize = progress
            }

            btnColor.tap {
                val color = fromColor(PrefManager.signalColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}