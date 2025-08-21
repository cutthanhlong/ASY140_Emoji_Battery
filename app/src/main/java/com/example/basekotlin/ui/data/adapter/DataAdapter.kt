package com.example.basekotlin.ui.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ItemDataStyleBinding
import com.example.basekotlin.model.DataStyleModel

class DataAdapter(var onClick: (DataStyleModel) -> Unit) :
    BaseAdapter<DataStyleModel, ItemDataStyleBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemDataStyleBinding {
        return ItemDataStyleBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<DataStyleModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemDataStyleBinding, item: DataStyleModel, layoutPosition: Int
    ) {
        binding.apply {
            tvStyle.text = item.title

            tvStyle.setTextColor(if (item.isActive) "#0D5400".toColorInt() else "#020007".toColorInt())
            root.setBackgroundResource(if (item.isActive) R.drawable.bg_radius_16_stroke_01_s else R.drawable.bg_radius_16_stroke_01_sn)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemDataStyleBinding, item: DataStyleModel, layoutPosition: Int
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
