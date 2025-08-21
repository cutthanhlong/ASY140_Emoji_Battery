package com.example.basekotlin.ui.color_template

import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.toJson
import com.example.basekotlin.data.call_api.ColorTemplateModel
import com.example.basekotlin.databinding.ActivityColorTemplateBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.color_template.adapter.ColorTemplateAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager

class ColorTemplateActivity :
    BaseActivity<ActivityColorTemplateBinding>(ActivityColorTemplateBinding::inflate) {

    private lateinit var colorTemplateAdapter: ColorTemplateAdapter
    private var listColorTemplate: ArrayList<ColorTemplateModel> = arrayListOf()

    override fun getData() {
        super.getData()

        binding.rcvData.apply {
            setHasFixedSize(true)
            colorTemplateAdapter = ColorTemplateAdapter {
                PrefManager.templateValue = it.toJson()
                PrefManager.isUseTemplate = true
            }.apply {
                addListData(listColorTemplate)
            }
            adapter = colorTemplateAdapter
        }

        viewModel.loadEmojiData()
        viewModel.colorTemplates.observe(this) { colorTemplates ->
            listColorTemplate.clear()
            listColorTemplate.addAll(colorTemplates)
            colorTemplateAdapter.addListData(listColorTemplate)
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}