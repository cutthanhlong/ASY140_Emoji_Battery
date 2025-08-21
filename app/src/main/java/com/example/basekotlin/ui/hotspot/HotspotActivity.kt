package com.example.basekotlin.ui.hotspot

import android.content.res.ColorStateList
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityHotspotBinding
import com.example.basekotlin.dialog.color.ColorPickerDialog
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import com.example.basekotlin.util.customview.battery.fromColor


class HotspotActivity : BaseActivity<ActivityHotspotBinding>(ActivityHotspotBinding::inflate) {

    private var colorPickerDialog: ColorPickerDialog? = null

    override fun initView() {
        colorPickerDialog = ColorPickerDialog(this, onClick = {
            PrefManager.hotspotColor = it
            binding.ivColor.imageTintList =
                ColorStateList.valueOf(PrefManager.hotspotColor.toColorInt())
        })

        binding.apply {
            ivColor.imageTintList = ColorStateList.valueOf(PrefManager.hotspotColor.toColorInt())

            sbHotspot.progress = PrefManager.hotspotSize
            tvHotspot.text = getString(R.string.hotspot_s, "${PrefManager.hotspotSize}")
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            sbHotspot.onProgressChanged { progress, _ ->
                tvHotspot.text = getString(R.string.hotspot_s, "$progress")
                PrefManager.hotspotSize = progress
            }

            btnColor.tap {
                val color = fromColor(PrefManager.hotspotColor)
                colorPickerDialog!!.showDialog(getString(R.string.icon_color), color)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}