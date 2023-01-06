package com.danliren.apps.tangshi.ui.poetrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.danliren.apps.tangshi.AppConstants
import com.danliren.apps.tangshi.R
import com.danliren.apps.tangshi.databinding.FragmentPoetryListBinding
import com.danliren.apps.tangshi.ui.base.ViewBindingFragment
import com.danliren.apps.tangshi.ui.adapter.PoetryListAdapter

class PoetryListFragment : ViewBindingFragment<FragmentPoetryListBinding>() {

    private val viewModel: PoetryListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoetryListBinding.inflate(inflater, container, false)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

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
                activity?.findNavController(R.id.nav_host_fragment_activity_main)
                    ?.navigate(
                        R.id.navigation_poetry_pager,
                        bundleOf(
                            AppConstants.KEY_POSITION to position
                        )
                    )
            }
        }

        viewModel.poetrySections.observe(viewLifecycleOwner) {
            binding.indexBar.sections = it.keys.toTypedArray()
        }

        viewModel.loadPoetryList()

        return binding.root
    }
}