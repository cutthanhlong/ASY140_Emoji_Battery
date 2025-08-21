package com.example.basekotlin.ui.ringer

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityRingerBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class RingerActivity : BaseActivity<ActivityRingerBinding>(ActivityRingerBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun initView() {
        colorPickerDialog = ColorPickerDialog(this, onClick = {
            PrefManager.ringerColor = it
            binding.ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.ringerColor.toColorInt())
        })

        binding.apply {
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.ringerColor.toColorInt())

            sbRinger.progress = PrefManager.ringerSize
            tvRinger.text = getString(R.string.ringer_s, "${PrefManager.ringerSize}")
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            sbRinger.onProgressChanged { progress, _ ->
                tvRinger.text = getString(R.string.ringer_s, "$progress")
                PrefManager.ringerSize = progress
            }

            btnColor.tap {
                val color = fromColor(PrefManager.ringerColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}