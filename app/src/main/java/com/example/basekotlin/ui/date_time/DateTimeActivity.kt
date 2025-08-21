package com.example.basekotlin.ui.date_time

import android.content.res.ColorStateList
import android.widget.LinearLayout
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityDateTimeBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class DateTimeActivity : BaseActivity<ActivityDateTimeBinding>(ActivityDateTimeBinding::inflate) {

    private lateinit var colorPickerDialog: ColorPickerDialog
    private var type = "date"
    private var listTemplate = arrayListOf<LinearLayout>()
    private var position = 0

    override fun initView() {
        binding.apply {
            ivColorDate.imageTintList = ColorStateList.valueOf(PrefManager.dateColor.toColorInt())
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.timeTextColor.toColorInt())

            ivDate.setImageResource(if (PrefManager.isShowDate) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            ivSecond.setImageResource(if (PrefManager.timeShowSecond) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            ivAmPm.setImageResource(if (PrefManager.isTimeShowAmPm) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            viewDate.showState(PrefManager.isShowDate)

            sbDate.progress = PrefManager.dateSize
            tvDate.text = getString(R.string.date_s, "${PrefManager.dateSize}")

            sbTime.progress = PrefManager.timeTextSize
            tvTime.text = getString(R.string.time_s, "${PrefManager.timeTextSize}")

            colorPickerDialog = ColorPickerDialog(this@DateTimeActivity, onClick = {
                if (type == "date") {
                    PrefManager.dateColor = it
                    binding.ivColorDate.imageTintList =
                        ColorStateList.valueOf(PrefManager.dateColor.toColorInt())
                } else {
                    PrefManager.timeTextColor = it
                    binding.ivColor.imageTintList =
                        ColorStateList.valueOf(PrefManager.timeTextColor.toColorInt())
                }
            })

            listTemplate.add(btnTemplate01)
            listTemplate.add(btnTemplate02)
            listTemplate.add(btnTemplate03)
            listTemplate.add(btnTemplate04)
            listTemplate.add(btnTemplate05)
            listTemplate.add(btnTemplate06)

            onTemplate(PrefManager.dateFormatIndex)
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivDate.tap {
                PrefManager.isShowDate = !PrefManager.isShowDate
                ivDate.setImageResource(if (PrefManager.isShowDate) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
                viewDate.showState(PrefManager.isShowDate)
            }

            ivSecond.tap {
                PrefManager.timeShowSecond = !PrefManager.timeShowSecond
                ivSecond.setImageResource(if (PrefManager.timeShowSecond) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }

            ivAmPm.tap {
                PrefManager.isTimeShowAmPm = !PrefManager.isTimeShowAmPm
                ivAmPm.setImageResource(if (PrefManager.isTimeShowAmPm) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }

            sbDate.onProgressChanged { progress, _ ->
                tvDate.text = getString(R.string.date_s, "$progress")
                PrefManager.dateSize = progress
            }

            btnColorDate.tap {
                type = "date"
                val color = fromColor(PrefManager.dateColor)
                colorPickerDialog.showDialog(getString(R.string.icon_color), color)
            }

            sbTime.onProgressChanged { progress, _ ->
                tvTime.text = getString(R.string.time_s, "$progress")
                PrefManager.timeTextSize = progress
            }

            btnColor.tap {
                type = "time"
                val color = fromColor(PrefManager.timeTextColor)
                colorPickerDialog.showDialog(getString(R.string.icon_color), color)
            }

            for (i in listTemplate.indices) {
                listTemplate[i].tap {
                    if (position != i) {
                        PrefManager.dateFormatIndex = i
                        onTemplate(i)
                    }
                }
            }
        }

    }

    private fun onTemplate(pos: Int) {
        position = pos

        binding.btnTemplate01.setBackgroundResource(R.drawable.bg_radius_16)
        binding.btnTemplate02.setBackgroundResource(R.drawable.bg_radius_16)
        binding.btnTemplate03.setBackgroundResource(R.drawable.bg_radius_16)
        binding.btnTemplate04.setBackgroundResource(R.drawable.bg_radius_16)
        binding.btnTemplate05.setBackgroundResource(R.drawable.bg_radius_16)
        binding.btnTemplate06.setBackgroundResource(R.drawable.bg_radius_16)

        when (pos) {
            0 -> {
                binding.btnTemplate01.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }

            1 -> {
                binding.btnTemplate02.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }

            2 -> {
                binding.btnTemplate03.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }

            3 -> {
                binding.btnTemplate04.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }

            4 -> {
                binding.btnTemplate05.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }

            5 -> {
                binding.btnTemplate06.setBackgroundResource(R.drawable.bg_radius_16_stroke_01_s)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }
}