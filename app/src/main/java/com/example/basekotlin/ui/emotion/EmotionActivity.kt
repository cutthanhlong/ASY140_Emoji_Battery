package com.example.basekotlin.ui.emotion

import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.data.call_api.EmojiModel
import com.example.basekotlin.databinding.ActivityEmotionBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.emotion.adapter.EmotionAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager


class EmotionActivity : BaseActivity<ActivityEmotionBinding>(ActivityEmotionBinding::inflate) {

    private lateinit var emotionAdapter: EmotionAdapter
    private var listEmojis: ArrayList<EmojiModel> = arrayListOf()

    override fun getData() {
        super.getData()

        binding.rcvData.apply {
            setHasFixedSize(true)
            emotionAdapter = EmotionAdapter {
                PrefManager.emotionPath = it.url
            }.apply {
                addListData(listEmojis)
            }
            adapter = emotionAdapter
        }
        binding.ivEmotion.setImageResource(if (PrefManager.isShowEmotion) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)

        viewModel.loadEmojiData()
        viewModel.emojis.observe(this) { emojis ->
            listEmojis.clear()
            listEmojis.addAll(emojis)
            listEmojis.forEach { it.isActive = it.url == PrefManager.emotionPath }
            emotionAdapter.addListData(listEmojis)
        }
    }


    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivEmotion.tap {
                PrefManager.isShowEmotion = !PrefManager.isShowEmotion
                ivEmotion.setImageResource(if (PrefManager.isShowEmotion) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}