package cn.danliren.apps.tangshi.ui.authlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import cn.danliren.apps.tangshi.AppConstants
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.databinding.FragmentAuthListBinding
import cn.danliren.apps.tangshi.ui.adapter.StringListAdapter
import cn.danliren.apps.tangshi.ui.base.ViewBindingFragment

class AuthListFragment : ViewBindingFragment<FragmentAuthListBinding>() {

    private val viewModel: AuthListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthListBinding.inflate(inflater, container, false)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        viewModel.authList.observe(viewLifecycleOwner) { authList ->
            binding.recyclerView.adapter = StringListAdapter(authList) { position ->
                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                    .navigate(
                        R.id.navigation_poetry_list_of_auth,
                        bundleOf(AppConstants.KEY_AUTH to authList[position])
                    )
            }
        }

        viewModel.loadAuthList()

        return _binding?.root
    }
}