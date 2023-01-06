package com.danliren.apps.tangshi.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danliren.apps.tangshi.BuildConfig
import com.danliren.apps.tangshi.R
import com.danliren.apps.tangshi.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.toolbar.setupWithNavController(
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
        )

        binding.aboutDesigner.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(getString(R.string.url_designer))),
                    getString(R.string.label_designer)
                )
            )
        }
        binding.aboutDeveloper.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(getString(R.string.url_designer))),
                    getString(R.string.label_designer)
                )
            )
        }

        binding.aboutDeveloper.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(getString(R.string.url_developer))),
                    getString(R.string.label_developer)
                )
            )
        }

        binding.versionName.text = BuildConfig.VERSION_NAME

        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}