package com.example.basekotlin.ui.main

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.os.bundleOf
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.onScrollDirection
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.BatteryTemplateModel
import com.example.basekotlin.databinding.ActivityMainBinding
import com.example.basekotlin.dialog.exit.ExitAppDialog
import com.example.basekotlin.dialog.permission.PermissionDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.accessibility_service.AccessibilityServiceActivity
import com.example.basekotlin.ui.emoji_battery.EmojiBatteryCreateActivity
import com.example.basekotlin.ui.emoji_battery.EmojiBatteryEditActivity
import com.example.basekotlin.ui.gesture.GestureActivity
import com.example.basekotlin.ui.main.adapter.EmojiBatteryAdapter
import com.example.basekotlin.ui.notch.NotchActivity
import com.example.basekotlin.ui.setting.SettingActivity
import com.example.basekotlin.ui.status_bar.StatusBarActivity
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.PrefManager


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var listBatteryEmoji: ArrayList<BatteryTemplateModel> = arrayListOf()
    private lateinit var emojiBatteryAdapter: EmojiBatteryAdapter
    private var posNavi = 0

    override fun getData() {
        super.getData()

        binding.rcvBattery.apply {
            setHasFixedSize(true)
            emojiBatteryAdapter = EmojiBatteryAdapter {
                PrefManager.emojiSelectedIndex = it.urlIconName
                PrefManager.batterySelectedIndex = it.urlBgName
                startNextActivity(EmojiBatteryEditActivity::class.java, bundleOf().apply {
                    putParcelable("item_battery", it)
                })
            }.apply {
                addListData(listBatteryEmoji)
            }
            adapter = emojiBatteryAdapter
        }

        binding.viewLoading.visible()
        viewModel.loadEmojiData()
        viewModel.batteryTemplates.observe(this) { battery ->
            if (battery.isNotEmpty()) binding.viewLoading.gone()

            listBatteryEmoji.clear()
            listBatteryEmoji.addAll(battery)
            emojiBatteryAdapter.addListData(listBatteryEmoji)
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivSetting.tap { startNextActivity(SettingActivity::class.java, null) }

            tvStart.tap {
                PermissionDialog(this@MainActivity) {
                    startNextActivity(AccessibilityServiceActivity::class.java, null)
                }.show()
            }

            btnBattery.tap { if (posNavi != 0) onNavigation(0) }

            btnCustomize.tap { if (posNavi != 1) onNavigation(1) }

            btnStatusBar.tap { startNextActivity(StatusBarActivity::class.java, null) }

            btnGesture.tap { startNextActivity(GestureActivity::class.java, null) }

            btnNotch.tap { startNextActivity(NotchActivity::class.java, null) }

            ivScrollTop.tap {
                rcvBattery.scrollToPosition(0)
                btnCreate.visible()
                ivCreate.gone()
            }

            rcvBattery.onScrollDirection(
                onScrollUp = {
                    btnCreate.visible()
                    ivScrollTop.visible()
                    ivCreate.gone()
                },

                onScrollDown = {
                    btnCreate.gone()
                    ivScrollTop.gone()
                    ivCreate.visible()
                }
            )

            btnCreate.tap {
                PrefManager.emojiSelectedIndex = PrefManager.pathBatteryEmojiCreateIcon
                PrefManager.batterySelectedIndex = PrefManager.pathBatteryEmojiCreateBattery
                startNextActivity(EmojiBatteryCreateActivity::class.java, null)
            }

            ivCreate.tap {
                PrefManager.emojiSelectedIndex = PrefManager.pathBatteryEmojiCreateIcon
                PrefManager.batterySelectedIndex = PrefManager.pathBatteryEmojiCreateBattery
                startNextActivity(EmojiBatteryCreateActivity::class.java, null)
            }
        }
    }

    private fun onNavigation(pos: Int) {
        posNavi = pos
        binding.apply {
            viewFlipper.displayedChild = pos

            ivBattery.imageTintList =
                ColorStateList.valueOf(if (pos == 0) "#FFFFFF".toColorInt() else "#6B6B6B".toColorInt())
            ivCustomize.imageTintList =
                ColorStateList.valueOf(if (pos == 1) "#FFFFFF".toColorInt() else "#6B6B6B".toColorInt())

            tvBattery.setTextColor(if (pos == 0) "#FFFFFF".toColorInt() else "#6B6B6B".toColorInt())
            tvCustomize.setTextColor(if (pos == 1) "#FFFFFF".toColorInt() else "#6B6B6B".toColorInt())

            btnBattery.background = if (pos == 0) ContextCompat.getDrawable(
                this@MainActivity, R.drawable.bg_radius_100_gradient
            ) else null
            btnCustomize.background = if (pos == 1) ContextCompat.getDrawable(
                this@MainActivity, R.drawable.bg_radius_100_gradient
            ) else null
        }
    }

    override fun reloadAds() {
        super.reloadAds()
        emojiBatteryAdapter.disableActive()
    }

    override fun onResume() {
        super.onResume()
        binding.lnService.showState(!PermissionManager.isAccessibilityServiceEnabled(this))
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

    override fun onBack() {
        ExitAppDialog(this, onClick = {
            finishAffinity()
        }).show()
    }

}
