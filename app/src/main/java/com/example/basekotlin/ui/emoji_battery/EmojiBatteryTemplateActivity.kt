package com.example.basekotlin.ui.emoji_battery

import androidx.core.os.bundleOf
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.onScrollDirection
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.BatteryTemplateModel
import com.example.basekotlin.databinding.ActivityEmojiBatteryTemplateBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.ui.main.adapter.EmojiBatteryAdapter
import com.example.basekotlin.util.PrefManager

class EmojiBatteryTemplateActivity :
    BaseActivity<ActivityEmojiBatteryTemplateBinding>(ActivityEmojiBatteryTemplateBinding::inflate) {

    private var listBatteryEmoji: ArrayList<BatteryTemplateModel> = arrayListOf()
    private lateinit var emojiBatteryAdapter: EmojiBatteryAdapter

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

        viewModel.loadEmojiData()
        viewModel.batteryTemplates.observe(this) { battery ->
            listBatteryEmoji.clear()
            listBatteryEmoji.addAll(battery)
            emojiBatteryAdapter.addListData(listBatteryEmoji)
        }
    }

    override fun bindView() {
        super.bindView()
        binding.apply {

            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

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

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}