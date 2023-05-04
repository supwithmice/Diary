@file:Suppress("NAME_SHADOWING")

package com.supwithmice.diary.core

import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.models.Diary
import com.supwithmice.diary.models.Student
import com.supwithmice.diary.utils.log
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

suspend fun getAttachment(attachmentId: Int){
    val response = client.get(url + "attachments/$attachmentId")
    TODO("save attachment")
}

suspend fun getDiary(student: Student, yearId: Int, start: String? = null, end: String? = null): Diary? {

    val start = start ?: getFirstDay()
    val end = end ?: getLastDay()

    val diary: Diary = client.get(url + "student/diary") {
        url {
            parameters.append("studentId", student.studentId.toString())
            parameters.append("yearId", yearId.toString())
            parameters.append("weekStart", start)
            parameters.append("weekEnd", end)
            parameters.append("vers", SiteInformation.ver)
            parameters.append("withLaAssigns", "true")
        }
    }.body()

    return if (diary.message == "An error has occurred.") null else diary
}

//haven't got any overdue so can't test it lol
suspend fun getOverdue(student: Student, yearId: Int , start: String? = null, end: String? = null) {

    val start = start ?: getFirstDay()
    val end = end ?: getLastDay()

    val overdue: Diary = client.get(
        url + "student/diary/pastMandatory") {
        url {
            parameters.append("studentId", student.studentId.toString())
            parameters.append("yearId", yearId.toString())
            parameters.append("weekStart", start)
            parameters.append("weekEnd", end)
        }
    }.body()

    overdue.log()
}
