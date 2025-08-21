package com.example.basekotlin.ui.emotion.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.EmojiModel
import com.example.basekotlin.databinding.ItemEmotionBinding

class EmotionAdapter(var onClick: (EmojiModel) -> Unit) :
    BaseAdapter<EmojiModel, ItemEmotionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemEmotionBinding {
        return ItemEmotionBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<EmojiModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemEmotionBinding, item: EmojiModel, layoutPosition: Int
    ) {
        binding.apply {
            context?.let {
                viewLottie.visible()
                Glide.with(it).load(item.url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>?,
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
                    }).into(ivItem)
            }

            viewItem.setBackgroundResource(if (item.isActive) R.drawable.bg_radius_16_stroke_01_s else R.drawable.bg_radius_16_stroke_01_sn)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemEmotionBinding, item: EmojiModel, layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.fileName)
            onClick(item)
        }
    }

    fun setCheck(id: String) {
        listData.forEach { it.isActive = it.fileName == id }
        notifyDataSetChanged()
    }

}
