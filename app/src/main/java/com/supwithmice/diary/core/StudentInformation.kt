package com.supwithmice.diary.core

import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.models.CurrentYear
import com.supwithmice.diary.models.DiaryInit
import com.supwithmice.diary.models.Student
import io.ktor.client.call.*
import io.ktor.client.request.*

object StudentInformation {
    lateinit var student: Student
    var yearId = 0
    lateinit var currentYear: CurrentYear
    lateinit var ourDiary: DiaryInit

    suspend fun initData() {
        ourDiary = client.get(url + "student/diary/init").body()
        currentYear = client.get(url + "years/current").body()
        yearId = 10010 //currentYear.id
        student = ourDiary.students[0]
    }
}