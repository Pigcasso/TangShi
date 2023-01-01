package cn.danliren.apps.tangshi.ui.favoritelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cn.danliren.apps.tangshi.AppConstants
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.databinding.FragmentPoetryPagerBinding
import cn.danliren.apps.tangshi.ui.adapter.PoetryPagerAdapter
import cn.danliren.apps.tangshi.ui.base.ViewBindingFragment

class PoetryPagerOfFavoriteFragment : ViewBindingFragment<FragmentPoetryPagerBinding>() {

    private val viewModel: FavoriteListViewModel by activityViewModels()

    private var position: Int
        get() = requireArguments().getInt(AppConstants.KEY_POSITION)
        set(value) = requireArguments().putInt(AppConstants.KEY_POSITION, value)

    private val onPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@PoetryPagerOfFavoriteFragment.position = position
            viewModel.poetryList.value?.get(position)?.let { poetry ->
                viewModel.isFavorite(poetry)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoetryPagerBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        binding.toolbar.menu?.findItem(R.id.menu_favorite)?.let { menuItem ->
            val attrs = intArrayOf(com.google.android.material.R.attr.colorOnPrimary)
            val a = requireContext().obtainStyledAttributes(attrs)
            val color = a.getColorStateList(0)
            MenuItemCompat.setIconTintList(menuItem, color)
            a.recycle()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_favorite -> {
                    viewModel.poetryList.value?.get(position)?.let { poetry ->
                        viewModel.toggleFavorite(poetry)
                    }
                    true
                }
                else -> false
            }
        }

        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)

        viewModel.poetryList.observe(viewLifecycleOwner) {
            binding.viewPager.adapter = PoetryPagerAdapter(this, it)
            binding.viewPager.setCurrentItem(position, false)
        }

        viewModel.currentPoetry.observe(viewLifecycleOwner) {
            if (viewModel.poetryList.value?.get(position)?.id == it.id) {
                binding.toolbar.menu.findItem(R.id.menu_favorite)?.setIcon(
                    if (it.isFavorite) R.drawable.ic_baseline_star_24
                    else R.drawable.ic_baseline_star_border_24
                )
            }
        }

        viewModel.loadFavoriteList()

        return _binding?.root
    }

    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        super.onDestroyView()
    }
}