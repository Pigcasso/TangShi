package cn.danliren.apps.tangshi.ui.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import cn.danliren.apps.tangshi.AppConstants
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.databinding.FragmentPoetryListWithToolbarBinding
import cn.danliren.apps.tangshi.ui.adapter.PoetryListAdapter
import cn.danliren.apps.tangshi.ui.base.ViewBindingFragment

/**
 * 根据分类 [category] 获取所有作品
 */
class PoetryListOfCategoryFragment : ViewBindingFragment<FragmentPoetryListWithToolbarBinding>() {

    private val viewModel: CategoryListViewModel by activityViewModels()

    private val category: String by lazy { requireArguments().getString(AppConstants.KEY_CATEGORY)!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoetryListWithToolbarBinding.inflate(inflater, container, false)

        binding.toolbar.title = category
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        binding.indexBar.setIndexBarFilter { _, _, previewText ->
            if (previewText == null) {
                binding.textPoetrySort.isInvisible = true
            } else {
                binding.textPoetrySort.text = previewText
                binding.textPoetrySort.isInvisible = false
            }
            viewModel.poetrySections.value?.get(previewText)?.let { position ->
                binding.recyclerView.scrollToPosition(position)
            }
        }

        viewModel.poetryList.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = PoetryListAdapter(it) { position ->
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                    .navigate(
                        R.id.navigation_poetry_pager_of_category,
                        bundleOf(
                            AppConstants.KEY_CATEGORY to category,
                            AppConstants.KEY_POSITION to position
                        )
                    )
            }
        }

        viewModel.poetrySections.observe(viewLifecycleOwner) {
            binding.indexBar.sections = it.keys.toTypedArray()
        }

        viewModel.loadPoetryListByCategory(category)

        return _binding?.root
    }
}