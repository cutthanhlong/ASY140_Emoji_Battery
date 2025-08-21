package com.example.basekotlin.ui.how_to_use

import android.annotation.SuppressLint
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tap
import com.example.basekotlin.databinding.ActivityHowToUseBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.ui.accessibility_service.AccessibilityServiceActivity
import com.example.basekotlin.ui.how_to_use.adapter.HowToUseAdapter
import com.example.basekotlin.ui.how_to_use.fragment.HowToUse0Fragment
import com.example.basekotlin.ui.how_to_use.fragment.HowToUse1Fragment
import com.example.basekotlin.ui.how_to_use.fragment.HowToUse2Fragment
import com.example.basekotlin.util.PermissionManager

class HowToUseActivity : BaseActivity<ActivityHowToUseBinding>(ActivityHowToUseBinding::inflate) {


    override fun initView() {
        super.initView()

        val listFragment: ArrayList<Fragment> = arrayListOf()
        listFragment.add(HowToUse0Fragment())
        listFragment.add(HowToUse1Fragment())
        listFragment.add(HowToUse2Fragment())

        val adapter = HowToUseAdapter(this, listFragment)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setPage(position)
            }
        })
        binding.viewPager2.currentItem = 0
    }

    override fun bindView() {
        super.bindView()

        binding.apply {
            ivLeft.tap { onBack() }

            tvPrevious.tap {
                if (binding.viewPager2.currentItem > 0) binding.viewPager2.currentItem--
            }

            tvNext.tap {
                if (binding.viewPager2.currentItem < 2) {
                    binding.viewPager2.currentItem++
                } else {
                    if (PermissionManager.checkFullPermission(this@HowToUseActivity)) {
                        finishThisActivity()
                    } else {
                        startNextActivity(AccessibilityServiceActivity::class.java, null)
                    }
                }
            }
        }
    }

    private fun setPage(pos: Int) {
        binding.apply {
            tvPrevious.setTextColor(if (pos == 0) "#B2B0B6".toColorInt() else "#0D5400".toColorInt())

            binding.viewPager2.currentItem = pos

            when (pos) {
                0 -> {
                    ivDot01.setImageResource(R.drawable.ic_intro_s)
                    ivDot02.setImageResource(R.drawable.ic_intro_sn)
                    ivDot03.setImageResource(R.drawable.ic_intro_sn)
                    tvNext.text = getString(R.string.next)
                }

                1 -> {
                    ivDot01.setImageResource(R.drawable.ic_intro_sn2)
                    ivDot02.setImageResource(R.drawable.ic_intro_s)
                    ivDot03.setImageResource(R.drawable.ic_intro_sn)
                    tvNext.text = getString(R.string.next)
                }

                2 -> {
                    ivDot01.setImageResource(R.drawable.ic_intro_sn2)
                    ivDot02.setImageResource(R.drawable.ic_intro_sn2)
                    ivDot03.setImageResource(R.drawable.ic_intro_s)
                    tvNext.text = getString(R.string.got_it)
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.WHITE, this)
    }

}