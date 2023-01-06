package com.danliren.apps.tangshi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danliren.apps.tangshi.R
import com.danliren.apps.tangshi.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.imageView.isVisible = !resources.getBoolean(R.bool.is_night_mode)

        val navView: BottomNavigationView = binding.navView

        val navController =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_fragment_main)!!
                .findNavController()
        navView.setupWithNavController(navController)
        return _binding?.root
    }
}