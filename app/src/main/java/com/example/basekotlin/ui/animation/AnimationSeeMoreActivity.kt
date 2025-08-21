package com.example.basekotlin.ui.animation

import androidx.core.os.bundleOf
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.data.call_api.AnimationModel
import com.example.basekotlin.databinding.ActivityAnimationSeeMoreBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.animation.adapter.AnimationSeeMoreAdapter
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager

class AnimationSeeMoreActivity :
    BaseActivity<ActivityAnimationSeeMoreBinding>(ActivityAnimationSeeMoreBinding::inflate) {

    private lateinit var animationAdapter: AnimationSeeMoreAdapter
    private var listAnimation = arrayListOf<AnimationModel>()

    override fun getData() {
        super.getData()

        animationAdapter = AnimationSeeMoreAdapter {
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
            listAnimation.addAll(animations)
            animationAdapter.addListData(listAnimation)
        }
    }

    override fun initView() {
        super.initView()

        binding.apply {
            rcvData.apply {
                setHasFixedSize(true)
                adapter = animationAdapter
            }
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }


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