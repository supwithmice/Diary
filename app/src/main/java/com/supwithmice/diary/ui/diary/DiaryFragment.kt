package com.supwithmice.diary.ui.diary

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.supwithmice.diary.core.getFirstDay
import com.supwithmice.diary.core.getLastDay
import com.supwithmice.diary.core.setWeek
import com.supwithmice.diary.core.weekOffset
import com.supwithmice.diary.databinding.FragmentDiaryBinding
import com.supwithmice.diary.presentation.ParentDiaryAdapter
import com.supwithmice.diary.utils.log

class DiaryFragment() : Fragment() {

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
        fun update() {
            diaryViewModel.updateDiary()
            diaryViewModel.diary.observe(viewLifecycleOwner) {
                it.log()
                if (it != null) {
                    parentDiaryAdapter.addData(it.weekDays)
                } else { Toast.makeText(container.context, "Invalid Diary", Toast.LENGTH_SHORT).show() }
            }
        }
        binding.weekTextView.text = getFirstDay().substring(5, 10)+" - "+getLastDay().substring(5, 10)
        update()

        val timer = object: CountDownTimer(1000, 1000){
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() { update() }
        }

        binding.prevButton.setOnClickListener {
            weekOffset -= 1
            setWeek()
            binding.weekTextView.text = getFirstDay().substring(5, 10)+" - "+getLastDay().substring(5, 10)
            timer.cancel()
            timer.start()
        }

        binding.nextButton.setOnClickListener {
            weekOffset += 1
            setWeek()
            binding.weekTextView.text = getFirstDay().substring(5, 10)+" - "+getLastDay().substring(5, 10)
            timer.cancel()
            timer.start()
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}