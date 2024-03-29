package com.danliren.apps.tangshi.ui.authlist

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
import com.danliren.apps.tangshi.databinding.FragmentPoetryListWithToolbarBinding
import com.danliren.apps.tangshi.ui.adapter.PoetryListAdapter
import com.danliren.apps.tangshi.ui.base.ViewBindingFragment

class PoetryListOfAuthFragment : ViewBindingFragment<FragmentPoetryListWithToolbarBinding>() {

    private val auth: String by lazy { requireArguments().getString(AppConstants.KEY_AUTH)!! }

    private val viewModel: AuthListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoetryListWithToolbarBinding.inflate(inflater, container, false)

        binding.toolbar.title = getString(R.string.title_poetry_list_of_auth, auth)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
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
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                    .navigate(
                        R.id.navigation_poetry_pager_of_auth,
                        bundleOf(
                            AppConstants.KEY_AUTH to auth,
                            AppConstants.KEY_POSITION to position
                        )
                    )
            }
        }

        viewModel.poetrySections.observe(viewLifecycleOwner) {
            binding.indexBar.sections = it.keys.toTypedArray()
        }

        viewModel.loadPoetryListByAuth(auth)

        return _binding?.root
    }
}