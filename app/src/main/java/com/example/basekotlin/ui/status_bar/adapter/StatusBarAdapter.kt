package com.example.basekotlin.ui.status_bar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ItemStatusBarCustomBinding
import com.example.basekotlin.model.StatusBarCustomModel

class StatusBarAdapter(var onClick: (StatusBarCustomModel) -> Unit) :
    BaseAdapter<StatusBarCustomModel, ItemStatusBarCustomBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemStatusBarCustomBinding {
        return ItemStatusBarCustomBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<StatusBarCustomModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ItemStatusBarCustomBinding, item: StatusBarCustomModel, layoutPosition: Int
    ) {
        binding.apply {
            tvCustom.text = item.title
            context?.let { Glide.with(it).load(item.image).into(ivCustom) }
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(
        binding: ItemStatusBarCustomBinding, item: StatusBarCustomModel, layoutPosition: Int
    ) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            onClick(item)
        }
    }

}
