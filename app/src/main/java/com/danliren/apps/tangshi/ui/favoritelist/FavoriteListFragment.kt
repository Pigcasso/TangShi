package com.danliren.apps.tangshi.ui.favoritelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.danliren.apps.tangshi.AppConstants
import com.danliren.apps.tangshi.R
import com.danliren.apps.tangshi.databinding.FragmentFavoriteListBinding
import com.danliren.apps.tangshi.ui.adapter.PoetryListAdapter
import com.danliren.apps.tangshi.ui.base.ViewBindingFragment

class FavoriteListFragment : ViewBindingFragment<FragmentFavoriteListBinding>() {

    private val viewModel: FavoriteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        viewModel.poetryList.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = PoetryListAdapter(it) { position ->
                activity?.findNavController(R.id.nav_host_fragment_activity_main)
                    ?.navigate(
                        R.id.action_navigation_favorite_list_to_navigation_poetry_pager_of_favorite,
                        bundleOf(AppConstants.KEY_POSITION to position)
                    )
            }
        }

        viewModel.loadFavoriteList()

        return _binding?.root
    }
}