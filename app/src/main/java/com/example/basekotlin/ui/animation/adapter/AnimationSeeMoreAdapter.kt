package com.example.basekotlin.ui.animation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.airbnb.lottie.LottieCompositionFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.AnimationModel
import com.example.basekotlin.databinding.ItemAnimationSeeMoreBinding
import com.example.basekotlin.util.PrefManager

class AnimationSeeMoreAdapter(var onClick: (AnimationModel) -> Unit) :
    BaseAdapter<AnimationModel, ItemAnimationSeeMoreBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemAnimationSeeMoreBinding {
        return ItemAnimationSeeMoreBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<AnimationModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemAnimationSeeMoreBinding, item: AnimationModel, layoutPosition: Int
    ) {
        binding.apply {
            context?.let {
                Glide.with(it).load(item.animationImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewLottie.gone()

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewLottie.gone()
                        return false
                    }
                }).into(ivAnimation)
            }

            ivActive.showState(item.isActive)
            rlActive.setBackgroundResource(if (item.isActive) R.drawable.bg_radius_16_stroke_01_animation else R.drawable.bg_radius_16)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemAnimationSeeMoreBinding, item: AnimationModel, layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.stt)
            onClick(item)
        }
    }

    fun setCheck(id: Int) {
        listData.forEach { it.isActive = it.stt == id }
        notifyDataSetChanged()
    }

}
