package com.example.basekotlin.ui.animation

import androidx.core.os.bundleOf
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.onProgressChanged
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.data.call_api.AnimationModel
import com.example.basekotlin.databinding.ActivityAnimationBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.animation.adapter.AnimationAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager


class AnimationActivity :
    BaseActivity<ActivityAnimationBinding>(ActivityAnimationBinding::inflate) {

    private lateinit var animationAdapter: AnimationAdapter
    private var listAnimation = arrayListOf<AnimationModel>()

    override fun getData() {
        super.getData()

        animationAdapter = AnimationAdapter {
            startNextActivity(
                AnimationPreviewActivity::class.java, bundleOf().apply {
                    putParcelable("item_animation", it)
                })
        }.apply {
            addListData(listAnimation)
            setCheck(PrefManager.animationId)
        }

        viewModel.loadEmojiData()
        viewModel.animations.observe(this) { animations ->
            listAnimation.clear()
            listAnimation.addAll(animations)
            animationAdapter.addListData(listAnimation)
        }
    }

    override fun initView() {
        binding.apply {
            ivBattery.setImageResource(if (PrefManager.isShowAnimation) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)

            sbAnimation.progress = PrefManager.animationSize
            tvAnimation.text = getString(R.string.animation_s, "${PrefManager.animationSize}")

            rcvData.apply {
                setHasFixedSize(true)
                adapter = animationAdapter
            }
        }
    }

    override fun bindView() {
        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            ivBattery.tap {
                PrefManager.isShowAnimation = !PrefManager.isShowAnimation
                ivBattery.setImageResource(if (PrefManager.isShowAnimation) R.drawable.ic_gesture_sw_s else R.drawable.ic_gesture_sw_sn)
            }

            tvSeeMore.tap {
                startNextActivity(AnimationSeeMoreActivity::class.java, null)
            }

            sbAnimation.onProgressChanged { progress, _ ->
                tvAnimation.text = getString(R.string.animation_s, "$progress")
                PrefManager.animationSize = progress
            }
        }

    }

    override fun reloadAds() {
        super.reloadAds()
        animationAdapter.setCheck(PrefManager.animationId)
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}