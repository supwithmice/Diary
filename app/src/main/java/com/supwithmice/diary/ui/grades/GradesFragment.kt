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
import com.supwithmice.diary.core.getStudentTotal
import com.supwithmice.diary.databinding.FragmentGradesBinding
import com.supwithmice.diary.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener


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

        //placeholder
        val unencodedHtml = getString(R.string.asd)
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        webView.loadData(encodedHtml, "text/html", null)
        webView.loadData(unencodedHtml, "text/html; charset=utf-8", "UTF-8")

        CoroutineScope(Dispatchers.IO).launch {
            getStudentTotal()
        }


        val eventSourceListener = object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: Response) {
                super.onOpen(eventSource, response)
                "Connection Opened".log()
            }

            override fun onClosed(eventSource: EventSource) {
                super.onClosed(eventSource)
                "Connection Closed".log()
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                super.onEvent(eventSource, id, type, data)
                "On Event Received! Data -: $data".log()
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                super.onFailure(eventSource, t, response)
            "On Failure -: ${response?.body}".log()
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}