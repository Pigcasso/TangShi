package com.danliren.apps.tangshi.ui.poetrydetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.danliren.apps.tangshi.AppConstants
import com.danliren.apps.tangshi.data.Poetry
import com.danliren.apps.tangshi.databinding.FragmentPoetryDetailsBinding

class PoetryDetailsFragment : Fragment() {

    private var _binding: FragmentPoetryDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoetryDetailsBinding.inflate(inflater, container, false)

        val poetry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments()
                .getSerializable(AppConstants.KEY_POETRY_DETAILS, Poetry::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            requireArguments()
                .getSerializable(AppConstants.KEY_POETRY_DETAILS)!! as Poetry
        }
        with(binding) {
            title.text = HtmlCompat.fromHtml(poetry.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            auth.text = HtmlCompat.fromHtml(poetry.auth, HtmlCompat.FROM_HTML_MODE_LEGACY)
            content.text = HtmlCompat.fromHtml(poetry.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            desc.text = HtmlCompat.fromHtml(poetry.desc, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(poetry: Poetry): PoetryDetailsFragment {
            val args = Bundle()
            args.putSerializable(AppConstants.KEY_POETRY_DETAILS, poetry)
            val fragment = PoetryDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}