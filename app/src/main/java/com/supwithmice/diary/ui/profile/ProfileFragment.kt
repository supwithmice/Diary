package com.supwithmice.diary.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.supwithmice.diary.InitialLogin
import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.core.DiaryData
import com.supwithmice.diary.core.SettingsModule.diaryPassword
import com.supwithmice.diary.core.SettingsModule.diaryUsername
import com.supwithmice.diary.core.client
import com.supwithmice.diary.databinding.FragmentProfileBinding
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.nameTextView.text = DiaryData.student.nickName
        binding.phoneEditText.setText(DiaryData.diarySettings.mobilePhone)
        binding.emailEditText.setText(DiaryData.diarySettings.email)
        binding.logOutTextView.setOnClickListener {
            diaryUsername = null
            diaryPassword = null
            startActivity(Intent(container!!.context, InitialLogin::class.java))
            activity?.finish()
        }

        binding.saveAllButton.setOnClickListener { with(binding) {
            DiaryData.diarySettings.mobilePhone = phoneEditText.text.toString()
            DiaryData.diarySettings.email = emailEditText.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                client.post(url + "webapi/mysettings/") {
                    contentType(ContentType.Application.Json)
                    setBody(Gson().toJson(DiaryData.diarySettings))
                }
            }

        }}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}