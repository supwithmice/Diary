package com.supwithmice.diary.ui.grades

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.supwithmice.diary.R
import com.supwithmice.diary.databinding.FragmentGradesBinding


class GradesFragment : Fragment() {

    private var _binding: FragmentGradesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(GradesViewModel::class.java)

        _binding = FragmentGradesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val webView: WebView = binding.webview

        val unencodedHtml = getString(R.string.asd)
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        webView.loadData(unencodedHtml, "text/html", null)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}