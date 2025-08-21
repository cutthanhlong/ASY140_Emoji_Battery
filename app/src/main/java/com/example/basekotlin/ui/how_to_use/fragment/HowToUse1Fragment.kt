package com.example.basekotlin.ui.how_to_use.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basekotlin.base.BaseFragment
import com.example.basekotlin.databinding.FragmentHowToUseBinding

class HowToUse1Fragment : BaseFragment<FragmentHowToUseBinding>() {
    override fun setBinding(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): FragmentHowToUseBinding {
        return FragmentHowToUseBinding.inflate(inflater!!, container, false)
    }

    override fun initView() {
        super.initView()
        binding.viewFlipper.displayedChild = 1
    }

}