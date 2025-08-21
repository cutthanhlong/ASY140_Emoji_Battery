package com.example.basekotlin.dialog.gesture_action

import android.content.Context
import com.example.basekotlin.base.BaseDialog
import com.example.basekotlin.databinding.DialogGestureActionBinding
import com.example.basekotlin.model.GestureModel
import com.example.basekotlin.ui.gesture.adapter.GestureAdapter
import com.example.basekotlin.util.InsertListManager

class GestureActionDialog(context: Context, var onClick: (GestureModel) -> Unit) :
    BaseDialog<DialogGestureActionBinding>(context, true) {

    override fun setBinding(): DialogGestureActionBinding {
        return DialogGestureActionBinding.inflate(layoutInflater)
    }

    private lateinit var gestureAdapter: GestureAdapter

    override fun initView() {
        binding.rcvAction.apply {
            gestureAdapter = GestureAdapter {
                dismiss()
                onClick(it)
            }.apply {
                addListData(InsertListManager.getListGestureAction())
            }
            adapter = gestureAdapter
        }

    }

    override fun bindView() {

    }

    fun showDialog(item: GestureModel) {
        if (!isShowing) show()

        gestureAdapter.setCheck(item)
    }

}