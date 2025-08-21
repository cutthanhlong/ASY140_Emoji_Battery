package com.example.basekotlin.ui.gesture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ItemGestureActionBinding
import com.example.basekotlin.model.GestureModel

class GestureAdapter(var onClick: (GestureModel) -> Unit) :
    BaseAdapter<GestureModel, ItemGestureActionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemGestureActionBinding {
        return ItemGestureActionBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<GestureModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemGestureActionBinding,
        item: GestureModel,
        layoutPosition: Int
    ) {
        binding.apply {
            tvAction.text = item.title

            if (item.isActive) {
                tvAction.setBackgroundColor("#DCEED9".toColorInt())
            } else {
                tvAction.setBackgroundColor("#FFFFFF".toColorInt())
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemGestureActionBinding,
        item: GestureModel,
        layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item)
            onClick(item)
        }
    }

    fun setCheck(item: GestureModel) {
        listData.forEach { it.isActive = it.id == item.id }
        notifyDataSetChanged()
    }

}
