package com.example.basekotlin.ui.how_to_use.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HowToUseAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: List<Fragment>,
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int = fragments.size

    fun getFragmentAtPosition(position: Int): Fragment {
        return fragments[position]
    }
}