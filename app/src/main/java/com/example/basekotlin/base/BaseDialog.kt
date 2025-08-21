package com.example.basekotlin.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.viewbinding.ViewBinding
import com.example.basekotlin.R
import com.example.basekotlin.util.SystemUtil

abstract class BaseDialog<VB : ViewBinding>(context: Context, var canAble: Boolean) :
    Dialog(context, R.style.BaseDialog) {

    lateinit var binding: VB
    protected abstract fun setBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        SystemUtil.setLocale(context)
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = setBinding()
        setContentView(binding.root)
        setCancelable(canAble)

        initView()
        bindView()
    }

    open fun initView() {

    }

    open fun bindView() {

    }

}