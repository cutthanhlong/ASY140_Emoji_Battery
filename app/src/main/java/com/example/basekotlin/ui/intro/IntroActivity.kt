package com.example.basekotlin.ui.intro

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.basekotlin.R
import com.example.basekotlin.base.BaseActivity
import com.example.basekotlin.base.setTintStatus
import com.example.basekotlin.base.tapNoHandle
import com.example.basekotlin.databinding.ActivityIntroBinding
import com.example.basekotlin.model.EnumStatusColor
import com.example.basekotlin.model.IntroModel
import com.example.basekotlin.ui.intro.adapter.IntroAdapter
import com.example.basekotlin.ui.main.MainActivity
import com.example.basekotlin.ui.permission.PermissionActivity
import com.example.basekotlin.util.InsertListManager
import com.example.basekotlin.util.PermissionManager
import com.example.basekotlin.util.SharedPreUtils
import kotlin.math.abs

class IntroActivity : BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    private var listDots: ArrayList<ImageView> = arrayListOf()
    private var listIntro: ArrayList<IntroModel> = arrayListOf()

    override fun initView() {
        listDots.addAll(
            InsertListManager.getListDots(
                arrayOf(
                    binding.ivCircle01, binding.ivCircle02, binding.ivCircle03, binding.ivCircle04
                )
            )
        )
        listIntro.addAll(InsertListManager.getListIntro())

        binding.viewPager2.adapter = IntroAdapter {
            binding.viewPager2.currentItem += 1
        }.apply {
            addListData(listIntro)
        }
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(100))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.8f + r * 0.2f
            val absPosition = abs(position)
            page.alpha = 1.0f - (1.0f - 0.3f) * absPosition
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeContentInit(position)
            }
        })
    }

    override fun bindView() {
        binding.btnNext.tapNoHandle {
            if (binding.viewPager2.currentItem == listIntro.size - 1) {
                startNextActivity()
            } else {
                binding.viewPager2.currentItem += 1
            }
        }
    }

    private fun startNextActivity() {
        if (SharedPreUtils.getInstance()
                .getCountOpenApp(this) > 1 && PermissionManager.checkFullPermission(this)
        ) {
            startNextActivity(MainActivity::class.java, null)
        } else {
            startNextActivity(PermissionActivity::class.java, null)
        }
        finish()
    }

    private fun changeContentInit(position: Int) {
        for (i in 0..listDots.size - 1) {
            listDots[i].setImageResource(
                if (i < position) {
                    R.drawable.ic_intro_sn2
                } else if (i == position) {
                    R.drawable.ic_intro_s
                } else {
                    R.drawable.ic_intro_sn
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.statusBar.status.setTintStatus(EnumStatusColor.BACKGROUND, this)
    }

    override fun onBack() {
        finishAffinity()
    }
}