package com.supwithmice.diary.utils

import com.supwithmice.diary.models.CurrentYear
import com.supwithmice.diary.models.DiaryInit
import com.supwithmice.diary.models.Student

object OurValues {
    lateinit var student: Student
    var yearId = 0
    lateinit var currentYear: CurrentYear
    lateinit var ourDiary: DiaryInit
}