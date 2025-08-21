package com.example.basekotlin.ui.emoji_battery.adapter

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
import com.example.basekotlin.data.call_api.BatteryModel
import com.example.basekotlin.databinding.ItemEmojiBatteryEditBinding

class BatteryAdapter(var onClick: (BatteryModel) -> Unit) :
    BaseAdapter<BatteryModel, ItemEmojiBatteryEditBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemEmojiBatteryEditBinding {
        return ItemEmojiBatteryEditBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<BatteryModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemEmojiBatteryEditBinding, item: BatteryModel, layoutPosition: Int
    ) {
        binding.apply {
            context?.let {
                viewLottie.visible()
                Glide.with(it)
                    .load(item.url)
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
                    })
                    .into(ivPinBg)
            }

            root.setBackgroundResource(if (item.isActive) R.drawable.bg_radius_16_stroke_01_s else R.drawable.bg_radius_16)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemEmojiBatteryEditBinding, item: BatteryModel, layoutPosition: Int
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
