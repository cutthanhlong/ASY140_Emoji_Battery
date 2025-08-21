package com.example.basekotlin.ui.emoji_battery

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityEmojiBatteryBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class EmojiBatteryActivity :
    BaseActivity<ActivityEmojiBatteryBinding>(ActivityEmojiBatteryBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null
    private var type = "percentage"

    override fun initView() {
        binding.apply {
            ivBattery.setImageResource(if (PrefManager.isShowEmoji) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            viewAppearance.showState(!PrefManager.isShowEmoji)

            sbPercentage.progress = PrefManager.batteryPercentageTextSize
            tvPercentage.text =
                getString(R.string.percentage_s, "${PrefManager.batteryPercentageTextSize}")

            sbEmojiBattery.progress = PrefManager.emojiSize
            tvEmojiBattery.text = getString(R.string.emoji_battery_s, "${PrefManager.emojiSize}")

            ivPercentage.imageTintList =
                ColorStateList.valueOf(PrefManager.batteryDefaultPercentageColor.toColorInt())
            ivBackground.imageTintList =
                ColorStateList.valueOf(PrefManager.batteryDefaultBackgroundColor.toColorInt())

            colorPickerDialog = ColorPickerDialog(this@EmojiBatteryActivity, onClick = {
                if (type == "percentage") {
                    PrefManager.batteryDefaultPercentageColor = it
                    ivPercentage.imageTintList =
                        ColorStateList.valueOf(PrefManager.batteryDefaultPercentageColor.toColorInt())
                } else {
                    PrefManager.batteryDefaultBackgroundColor = it
                    ivBackground.imageTintList =
                        ColorStateList.valueOf(PrefManager.batteryDefaultBackgroundColor.toColorInt())
                }
            })
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivBattery.tap {
                PrefManager.isShowEmoji = !PrefManager.isShowEmoji
                ivBattery.setImageResource(if (PrefManager.isShowEmoji) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
                viewAppearance.showState(!PrefManager.isShowEmoji)
            }

            sbPercentage.onProgressChanged { progress, _ ->
                tvPercentage.text = getString(R.string.percentage_s, "$progress")
                PrefManager.batteryPercentageTextSize = progress
            }

            sbEmojiBattery.onProgressChanged { progress, _ ->
                tvEmojiBattery.text = getString(R.string.emoji_battery_s, "$progress")
                PrefManager.emojiSize = progress
            }

            btnTemplate.tap { startNextActivity(EmojiBatteryTemplateActivity::class.java, null) }

            btnPercentage.tap {
                type = "percentage"
                val color = fromColor(PrefManager.batteryDefaultPercentageColor)
                colorPickerDialog!!.showDialog(getString(R.string.percentage), color)
            }

            btnBackground.tap {
                type = "background"
                val color = fromColor(PrefManager.batteryDefaultBackgroundColor)
                colorPickerDialog!!.showDialog(getString(R.string.background), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}