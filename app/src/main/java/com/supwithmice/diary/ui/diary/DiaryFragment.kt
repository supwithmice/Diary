package com.supwithmice.diary.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.supwithmice.diary.databinding.FragmentDiaryBinding
import com.supwithmice.diary.presentation.ParentDiaryAdapter

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentDiaryAdapter: ParentDiaryAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val diaryViewModel = ViewModelProvider(this)[DiaryViewModel::class.java]

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.parentDiaryRecyclerView.layoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.VERTICAL,false)
        parentDiaryAdapter = ParentDiaryAdapter()
        binding.parentDiaryRecyclerView.adapter = parentDiaryAdapter
        diaryViewModel.diary.observe(viewLifecycleOwner) {
            if (it != null) {
                parentDiaryAdapter.addData(it.weekDays)
            } else { Toast.makeText(container.context, "Invalid Diary", Toast.LENGTH_SHORT).show() }
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}