package com.example.basekotlin.ui.language.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseAdapter
import com.example.basekotlin.base.gone
import com.example.basekotlin.base.setBackground
import com.example.basekotlin.base.tap
import com.example.basekotlin.model.LanguageModel
import com.example.basekotlin.databinding.ItemLanguageBinding

class LanguageAdapter(var onClick: (String) -> Unit) :
    BaseAdapter<LanguageModel, ItemLanguageBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int,
    ): ItemLanguageBinding {
        return ItemLanguageBinding.inflate(inflater, parent, false)
    }

    override fun addListData(newList: MutableList<LanguageModel>) {
        listData.clear()
        listData.addAll(newList)
        notifyDataSetChanged()
    }

    override fun setData(binding: ItemLanguageBinding, item: LanguageModel, layoutPosition: Int) {
        binding.apply {
            tvLang.text = item.name

            if (item.active) {
                root.setBackgroundResource(R.drawable.bg_lang_item_s)
            } else {
                root.setBackgroundResource(R.drawable.bg_lang_item_sn)
            }

            context?.let {
                Glide.with(it).asBitmap().load(item.image).into(icLang)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun onCLick(binding: ItemLanguageBinding, item: LanguageModel, layoutPosition: Int) {
        super.onCLick(binding, item, layoutPosition)
        binding.root.tap {
            setCheck(item.code)
            onClick(item.code)
        }
    }

    fun setCheck(code: String?) {
        listData.forEach { it.active = it.code == code }
        notifyDataSetChanged()
    }

}
