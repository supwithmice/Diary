package com.supwithmice.diary.core

import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.models.CurrentYear
import com.supwithmice.diary.models.DiaryGeneral
import com.supwithmice.diary.models.DiarySettings
import com.supwithmice.diary.models.Student
import io.ktor.client.call.*
import io.ktor.client.request.*

object DiaryData {
    lateinit var student: Student
    var yearId = 0
    lateinit var currentYear: CurrentYear
    lateinit var diaryGeneral: DiaryGeneral
    lateinit var diarySettings: DiarySettings

    suspend fun initData() {
        diaryGeneral = client.get(url + "student/diary/init").body()
        currentYear = client.get(url + "years/current").body()
        diarySettings = client.get(url + "mysettings").body()
        yearId = currentYear.id
        student = diaryGeneral.students[0]
    }
}
