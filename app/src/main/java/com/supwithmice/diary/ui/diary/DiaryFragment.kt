package com.supwithmice.diary.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supwithmice.diary.core.StudentInformation.student
import com.supwithmice.diary.core.StudentInformation.yearId
import com.supwithmice.diary.core.getDiary
import com.supwithmice.diary.databinding.FragmentDiaryBinding
import com.supwithmice.diary.presentation.DiaryAdapter
import com.supwithmice.diary.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val diaryViewModel = ViewModelProvider(this)[DiaryViewModel::class.java]

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        CoroutineScope(Dispatchers.IO).launch {
            val diary = getDiary(student, yearId)
            diary.log()
            withContext(Dispatchers.Main) {
                with(binding) {
                    diaryRecyclerView1.layoutManager = LinearLayoutManager(container!!.context)
                    diaryRecyclerView1.adapter = DiaryAdapter(diary.weekDays[0].lessons)

                    diaryRecyclerView2.layoutManager = LinearLayoutManager(container!!.context)
                    diaryRecyclerView2.adapter = DiaryAdapter(diary.weekDays[1].lessons)

                    diaryRecyclerView3.layoutManager = LinearLayoutManager(container!!.context)
                    diaryRecyclerView3.adapter = DiaryAdapter(diary.weekDays[2].lessons)

                    diaryRecyclerView4.layoutManager = LinearLayoutManager(container!!.context)
                    diaryRecyclerView4.adapter = DiaryAdapter(diary.weekDays[3].lessons)

                    diaryRecyclerView5.layoutManager = LinearLayoutManager(container!!.context)
                    diaryRecyclerView5.adapter = DiaryAdapter(diary.weekDays[4].lessons)
                }
            }
        }

//        diaryViewModel.updateDiary()
//        diaryViewModel.diary.observe(viewLifecycleOwner) {
//            it.log()
//
//        }



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}