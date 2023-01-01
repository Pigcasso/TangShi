package cn.danliren.apps.tangshi.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.cleanCache.isVisible = false
        binding.cleanCache.setOnClickListener {
        }
        binding.history.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.navigation_history_list)
        }
        binding.myFavorites.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.navigation_favorite_list)
        }
        binding.feedback.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(getString(R.string.url_feedback)))
            )
        }
        binding.about.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.navigation_about)
        }
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}