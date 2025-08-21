package com.example.basekotlin.ui.status_bar

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityStatusBarBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.dialog.permission.PermissionDialog
import com.example.basekotlin.model.EnumColor
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.accessibility_service.AccessibilityServiceActivity
import com.example.basekotlin.ui.airplane.AirplaneActivity
import com.example.basekotlin.ui.animation.AnimationActivity
import com.example.basekotlin.ui.carrier_name.CarrierNameActivity
import com.example.basekotlin.ui.charge.ChargeActivity
import com.example.basekotlin.ui.color_template.ColorTemplateActivity
import com.example.basekotlin.ui.data.DataActivity
import com.example.basekotlin.ui.date_time.DateTimeActivity
import com.example.basekotlin.ui.emoji_battery.EmojiBatteryActivity
import com.example.basekotlin.ui.emotion.EmotionActivity
import com.example.basekotlin.ui.hotspot.HotspotActivity
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.ui.notch.NotchActivity
import com.example.basekotlin.ui.ringer.RingerActivity
import com.example.basekotlin.ui.signal.SignalActivity
import com.example.basekotlin.ui.status_bar.adapter.StatusBarAdapter
import com.example.basekotlin.ui.wifi.WifiActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor

class StatusBarActivity :
    BaseActivity<ActivityStatusBarBinding>(ActivityStatusBarBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null
    private var type = EnumColor.ICON

    override fun initView() {
        super.initView()

        colorPickerDialog = ColorPickerDialog(this, onClick = {
            when (type) {
                EnumColor.ICON -> {
                    PrefManager.iconColor = it
                    binding.ivColorIcon.imageTintList =
                        ColorStateList.valueOf(PrefManager.iconColor.toColorInt())
                }

                EnumColor.BACKGROUND -> {
                    PrefManager.backGroundViewColor = it
                    binding.ivColorBg.imageTintList =
                        ColorStateList.valueOf(PrefManager.backGroundViewColor.toColorInt())
                }
            }
        })

        binding.apply {
            ivStatus.setImageResource(if (PrefManager.isShowStatusBar) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            btnColorTemplate.showState(PrefManager.isShowStatusBar)
            viewCustom.showState(PrefManager.isShowStatusBar)

            tvHeight.text = getString(R.string.height_s, "${PrefManager.statusBarHeight}")
            sbHeight.progress = PrefManager.statusBarHeight

            tvLeftMargin.text = getString(R.string.left_margin_s, "${PrefManager.leftMargin}")
            sbLeftMargin.progress = PrefManager.leftMargin

            tvRightMargin.text = getString(R.string.right_margin_s, "${PrefManager.rightMargin}")
            sbRightMargin.progress = PrefManager.rightMargin

            binding.ivColorIcon.imageTintList =
                ColorStateList.valueOf(PrefManager.iconColor.toColorInt())
            binding.ivColorBg.imageTintList =
                ColorStateList.valueOf(PrefManager.backGroundViewColor.toColorInt())

            rcvCustom.apply {
                setHasFixedSize(true)
                adapter = StatusBarAdapter {
                    when (it.id) {
                        0 -> {
                            startNextActivity(EmojiBatteryActivity::class.java, null)
                        }

                        1 -> {
                            startNextActivity(WifiActivity::class.java, null)
                        }

                        2 -> {
                            startNextActivity(DataActivity::class.java, null)
                        }

                        3 -> {
                            startNextActivity(SignalActivity::class.java, null)
                        }

                        4 -> {
                            startNextActivity(AirplaneActivity::class.java, null)
                        }

                        5 -> {
                            startNextActivity(HotspotActivity::class.java, null)
                        }

                        6 -> {
                            startNextActivity(RingerActivity::class.java, null)
                        }

                        7 -> {
                            startNextActivity(DateTimeActivity::class.java, null)
                        }

                        8 -> {
                            startNextActivity(NotchActivity::class.java, null)
                        }

                        9 -> {
                            startNextActivity(CarrierNameActivity::class.java, null)
                        }

                        10 -> {
                            startNextActivity(AnimationActivity::class.java, null)
                        }

                        11 -> {
                            startNextActivity(ChargeActivity::class.java, null)
                        }

                        12 -> {
                            startNextActivity(EmotionActivity::class.java, null)
                        }
                    }
                }.apply {
                    addListData(InsertListManager.getListStatusBarCustom())
                }
            }
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivStatus.tap {
                if (PermissionManager.checkFullPermission(this@StatusBarActivity)) {
                    PrefManager.isShowStatusBar = !PrefManager.isShowStatusBar
                    ivStatus.setImageResource(if (PrefManager.isShowStatusBar) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
                    btnColorTemplate.showState(PrefManager.isShowStatusBar)
                    viewCustom.showState(PrefManager.isShowStatusBar)
                } else {
                    PermissionDialog(this@StatusBarActivity) {
                        startNextActivity(AccessibilityServiceActivity::class.java, null)
                    }.show()
                }
            }

            sbHeight.onProgressChanged { progress, _ ->
                tvHeight.text = getString(R.string.height_s, "$progress")
                PrefManager.statusBarHeight = progress
            }

            sbLeftMargin.onProgressChanged { progress, _ ->
                tvLeftMargin.text = getString(R.string.left_margin_s, "$progress")
                PrefManager.leftMargin = progress
            }

            sbRightMargin.onProgressChanged { progress, _ ->
                tvRightMargin.text = getString(R.string.right_margin_s, "$progress")
                PrefManager.rightMargin = progress
            }

            btnColorIcon.tap {
                type = EnumColor.ICON
                val color = fromColor(PrefManager.iconColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }

            btnColorBg.tap {
                type = EnumColor.BACKGROUND
                val color = fromColor(PrefManager.backGroundViewColor)
                colorPickerDialog!!.showDialog(getString(R.string.background), color)
            }

            btnColorTemplate.tap {
                startNextActivity(ColorTemplateActivity::class.java, null)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}