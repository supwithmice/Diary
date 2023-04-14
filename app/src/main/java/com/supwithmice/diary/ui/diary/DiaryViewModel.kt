package com.supwithmice.diary.ui.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supwithmice.diary.core.StudentInformation
import com.supwithmice.diary.core.getDiary
import com.supwithmice.diary.models.Diary
import kotlinx.coroutines.launch

class DiaryViewModel : ViewModel() {

    private val _diary = MutableLiveData<Diary?>()
    val diary: LiveData<Diary?> = _diary

    fun updateDiary() = viewModelScope.launch {
        _diary.value = getDiary(StudentInformation.student, StudentInformation.yearId)
    }
}