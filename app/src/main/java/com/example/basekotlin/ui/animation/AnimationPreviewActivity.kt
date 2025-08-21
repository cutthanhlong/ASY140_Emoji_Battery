package com.example.basekotlin.ui.animation

import android.os.Build
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieCompositionFactory
import com.bumptech.glide.Glide
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.AnimationModel
import com.example.basekotlin.databinding.ActivityAnimationPreviewBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.how_to_use.HowToUseActivity
import com.example.basekotlin.util.PrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimationPreviewActivity :
    BaseActivity<ActivityAnimationPreviewBinding>(ActivityAnimationPreviewBinding::inflate) {

    private lateinit var item: AnimationModel

    override fun getData() {
        super.getData()

        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item_animation", AnimationModel::class.java)!!
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra("item_animation")!!
        }
    }

    override fun initView() {
        super.initView()

        binding.apply {
            LottieCompositionFactory.fromUrl(this@AnimationPreviewActivity, item.animationJsonUrl)
                .addListener { composition ->
                    viewLottie.visible()
                    ivAnimation.gone()

                    viewLottie.setComposition(composition)
                    viewLottie.requestLayout()
                    viewLottie.playAnimation()
                }.addFailureListener { exception ->
                    viewLottie.gone()
                    ivAnimation.visible()

                    Glide.with(this@AnimationPreviewActivity).asBitmap()
                        .load(item.animationImageUrl).into(ivAnimation)
                }
        }
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            ivRight.tap { startNextActivity(HowToUseActivity::class.java, null) }

            tvCancel.tap { onBack() }

            tvApply.tap {
                if (PrefManager.isShowAnimation) {
                    PrefManager.animationURL = item.animationJsonUrl
                    PrefManager.animationId = item.stt
                    onBack()
                } else {
                    tvError.visible()
                    lifecycleScope.launch {
                        delay(500)
                        tvError.gone()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}