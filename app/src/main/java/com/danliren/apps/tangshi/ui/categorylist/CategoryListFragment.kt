package com.danliren.apps.tangshi.ui.categorylist

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
import com.danliren.apps.tangshi.databinding.FragmentCategoryListBinding
import com.danliren.apps.tangshi.ui.adapter.StringListAdapter
import com.danliren.apps.tangshi.ui.base.ViewBindingFragment

class CategoryListFragment : ViewBindingFragment<FragmentCategoryListBinding>() {

    private val viewModel: CategoryListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        viewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            binding.recyclerView.adapter = StringListAdapter(categoryList) { position ->
                activity?.findNavController(R.id.nav_host_fragment_activity_main)
                    ?.navigate(
                        R.id.navigation_poetry_list_of_category,
                        bundleOf(
                            AppConstants.KEY_CATEGORY to categoryList[position]
                        )
                    )
            }
        }

        viewModel.loadCategoryList()

        return _binding?.root
    }
}