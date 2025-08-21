package com.example.basekotlin.ui.notch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.showState
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ItemNotchBinding
import com.example.basekotlin.model.NotchModel

class NotchAdapter(var onClick: (NotchModel) -> Unit) :
    BaseAdapter<NotchModel, ItemNotchBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemNotchBinding {
        return ItemNotchBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<NotchModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(binding: ItemNotchBinding, item: NotchModel, layoutPosition: Int) {
        binding.apply {
            if (item.isActive) {
                bgNotch.setBackgroundResource(R.drawable.bg_radius_16_top_notch_s)
            } else {
                bgNotch.setBackgroundResource(R.drawable.bg_radius_16_top)
            }
            tvHide.showState(item.id == 7)

            context?.let {
                Glide.with(it).asBitmap().load(item.image).into(ivNotch)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(binding: ItemNotchBinding, item: NotchModel, layoutPosition: Int) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.id)
            onClick(item)
        }
    }

    fun setCheck(code: Int) {
        listData.forEach { it.isActive = it.id == code }
        notifyDataSetChanged()
    }

}
