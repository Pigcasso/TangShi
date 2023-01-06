package com.danliren.apps.tangshi.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.danliren.apps.tangshi.data.Poetry
import com.danliren.apps.tangshi.ui.poetrydetails.PoetryDetailsFragment

class PoetryPagerAdapter(
    fragment: Fragment, private val items: List<Poetry>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return PoetryDetailsFragment.newInstance(items[position])
    }
}