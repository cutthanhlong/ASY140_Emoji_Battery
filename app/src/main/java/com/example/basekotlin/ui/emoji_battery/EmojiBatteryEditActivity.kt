package com.example.basekotlin.ui.emoji_battery

import android.content.res.ColorStateList
import android.os.Build
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.data.call_api.BatteryModel
import com.example.basekotlin.data.call_api.BatteryTemplateModel
import com.example.basekotlin.data.call_api.IconModel
import com.example.basekotlin.databinding.ActivityEmojiBaterryEditBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.dialog.permission.PermissionDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.accessibility_service.AccessibilityServiceActivity
import com.example.basekotlin.ui.emoji_battery.adapter.BatteryAdapter
import com.example.basekotlin.ui.emoji_battery.adapter.IconAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class EmojiBatteryEditActivity :
    BaseActivity<ActivityEmojiBaterryEditBinding>(ActivityEmojiBaterryEditBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    private var listBattery: ArrayList<BatteryModel> = arrayListOf()
    private var listIcon: ArrayList<IconModel> = arrayListOf()

    private lateinit var iconAdapter: IconAdapter
    private lateinit var batteryAdapter: BatteryAdapter

    private lateinit var item: BatteryTemplateModel


    override fun getData() {
        super.getData()

        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item_battery", BatteryTemplateModel::class.java)!!
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra("item_battery")!!
        }
        Glide.with(this).load(item.urlIconName).into(binding.ivPin)
        Glide.with(this).load(item.urlBgName).into(binding.ivPinBg)

        binding.rcvBattery.apply {
            setHasFixedSize(true)
            batteryAdapter = BatteryAdapter {
                item.urlBgName = it.url
                PrefManager.batterySelectedIndex = it.url
                updateIcon()
            }.apply {
                addListData(listBattery)
            }
            adapter = batteryAdapter
        }

        binding.rcvEmoji.apply {
            setHasFixedSize(true)
            iconAdapter = IconAdapter { data ->
                listIcon.forEach { it.isActive = it.code == data.code }

                item.urlIconName = data.url
                PrefManager.emojiSelectedIndex = data.url
                Glide.with(this).load(data.url).into(binding.ivPin)
            }.apply {
                addListData(listIcon)
            }
            adapter = iconAdapter
        }

        viewModel.loadEmojiData()
        viewModel.icons.observe(this) { icons ->
            listIcon.clear()
            listIcon.addAll(icons)

            listIcon.forEach { it.isActive = it.fileName.contains(item.iconName) }
            updateIcon()
            if (item.iconName != "") {
                val activePosition = listIcon.indexOfFirst { it.isActive }
                if (activePosition != -1) {
                    binding.rcvEmoji.smoothScrollToPosition(activePosition)
                }
            }
        }

        viewModel.batteries.observe(this) { batteries ->
            listBattery.clear()
            listBattery.addAll(batteries)

            updateBattery()
            if (item.batteryName != "") {
                val activePosition = listBattery.indexOfFirst { it.isActive }
                if (activePosition != -1) {
                    binding.rcvBattery.smoothScrollToPosition(activePosition)
                }
            }
        }
    }

    override fun initView() {
        binding.apply {
            sbPercentage.progress = PrefManager.batteryPercentageTextSize
            tvPercentage.text =
                getString(R.string.percentage_s, "${PrefManager.batteryPercentageTextSize}")

            sbEmojiBattery.progress = PrefManager.emojiSize
            tvEmojiBattery.text = getString(R.string.emoji_battery_s, "${PrefManager.emojiSize}")

            tvPin.showState(PrefManager.isShowBatteryPercentage)
            ivShowPercent.setImageResource(if (PrefManager.isShowBatteryPercentage) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            tvPin.setTextColor(fromColor(PrefManager.batteryPercentageColor))
            ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.batteryPercentageColor.toColorInt())

            colorPickerDialog = ColorPickerDialog(this@EmojiBatteryEditActivity, onClick = {
                PrefManager.batteryPercentageColor = it
                ivColor.imageTintList =
                    ColorStateList.valueOf(PrefManager.batteryPercentageColor.toColorInt())
                tvPin.setTextColor(fromColor(PrefManager.batteryPercentageColor))
            })
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivStatus.tap {
                PermissionDialog(this@EmojiBatteryEditActivity) {
                    startNextActivity(AccessibilityServiceActivity::class.java, null)
                }.show()
            }

            sbPercentage.onProgressChanged { progress, _ ->
                tvPercentage.text = getString(R.string.percentage_s, "$progress")
                PrefManager.batteryPercentageTextSize = progress
            }

            sbEmojiBattery.onProgressChanged { progress, _ ->
                tvEmojiBattery.text = getString(R.string.emoji_battery_s, "$progress")
                PrefManager.emojiSize = progress
            }

            ivShowPercent.tap {
                PrefManager.isShowBatteryPercentage = !PrefManager.isShowBatteryPercentage
                tvPin.showState(PrefManager.isShowBatteryPercentage)
                ivShowPercent.setImageResource(if (PrefManager.isShowBatteryPercentage) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }

            btnColor.tap {
                val color = fromColor(PrefManager.batteryPercentageColor)
                colorPickerDialog!!.showDialog(getString(R.string.percentage), color)
            }

        }

    }

    private fun updateIcon() {
        listIcon.forEach { it.battery = item.urlBgName }
        iconAdapter.addListData(listIcon)

        Glide.with(this).load(item.urlBgName).into(binding.ivPinBg)
    }

    private fun updateBattery() {
        listBattery.forEach { it.isActive = it.fileName.contains(item.batteryName) }
        batteryAdapter.addListData(listBattery)
    }

    override fun onResume() {
        super.onResume()
        binding.lnService.showState(!PermissionManager.isAccessibilityServiceEnabled(this))
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}