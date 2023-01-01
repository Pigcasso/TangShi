package cn.danliren.apps.tangshi.ui.historylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.databinding.FragmentHistoryListBinding
import cn.danliren.apps.tangshi.ui.adapter.PoetryListAdapter
import cn.danliren.apps.tangshi.ui.base.ViewBindingFragment

class HistoryListFragment : ViewBindingFragment<FragmentHistoryListBinding>() {

    private val viewModel: HistoryListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryListBinding.inflate(inflater, container, false)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigateUp()
        }

        viewModel.poetryList.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = PoetryListAdapter(it) { }
        }

        viewModel.loadHistoryList()

        return _binding?.root
    }
}