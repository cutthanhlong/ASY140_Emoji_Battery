package com.example.basekotlin.ui.charge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.tap
import com.example.basekotlin.base.visible
import com.example.basekotlin.databinding.ItemDataStyleBinding
import com.example.basekotlin.model.NotchModel

class ChargeAdapter(var onClick: (NotchModel) -> Unit) :
    BaseAdapter<NotchModel, ItemDataStyleBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemDataStyleBinding {
        return ItemDataStyleBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<NotchModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemDataStyleBinding, item: NotchModel, layoutPosition: Int
    ) {
        binding.apply {
            tvStyle.gone()
            ivStyle.visible()

            context?.let { Glide.with(it).load(item.image).into(ivStyle) }
            root.setBackgroundResource(if (item.isActive) R.drawable.bg_radius_16_stroke_01_s else R.drawable.bg_radius_16_stroke_01_sn)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemDataStyleBinding, item: NotchModel, layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.id)
            onClick(item)
        }
    }

    fun setCheck(item: Int) {
        listData.forEach { it.isActive = it.id == item }
        notifyDataSetChanged()
    }

}
