package com.example.basekotlin.ui.color_template.adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.data.call_api.ColorTemplateModel
import com.example.basekotlin.databinding.ItemColorTemplateBinding

class ColorTemplateAdapter(var onClick: (ColorTemplateModel) -> Unit) :
    BaseAdapter<ColorTemplateModel, ItemColorTemplateBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemColorTemplateBinding {
        return ItemColorTemplateBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<ColorTemplateModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemColorTemplateBinding, item: ColorTemplateModel, layoutPosition: Int
    ) {
        binding.apply {
            context?.let {
                viewLottie.visible()
                viewStatus.gone()

                Glide.with(it).load(item.url).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            viewLottie.gone()
                            viewStatus.visible()

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
                            viewStatus.visible()

                            return false
                        }
                    }).into(ivTemplate)
            }

            tvTime.setTextColor(item.hexColor.toColorInt())
            ivSignal.imageTintList = ColorStateList.valueOf(item.hexColor.toColorInt())
            ivWifi.imageTintList = ColorStateList.valueOf(item.hexColor.toColorInt())
            ivPin.imageTintList = ColorStateList.valueOf(item.hexColor.toColorInt())
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemColorTemplateBinding, item: ColorTemplateModel, layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.code)
            onClick(item)
        }
    }

    fun setCheck(id: String) {
        listData.forEach { it.isActive = it.code == id }
        notifyDataSetChanged()
    }

}
