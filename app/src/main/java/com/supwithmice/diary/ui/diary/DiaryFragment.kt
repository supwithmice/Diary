package com.supwithmice.diary.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.supwithmice.diary.databinding.FragmentDiaryBinding
import com.supwithmice.diary.log
import com.supwithmice.diary.utils.OurValues.student
import com.supwithmice.diary.utils.OurValues.yearId
import com.supwithmice.diary.utils.getDiary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(DiaryViewModel::class.java)

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        CoroutineScope(Dispatchers.IO).launch {
            val diary = getDiary(student, yearId)
            diary.log()
        }



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}