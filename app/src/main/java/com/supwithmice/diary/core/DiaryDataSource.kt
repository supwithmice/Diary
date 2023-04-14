@file:Suppress("NAME_SHADOWING")

package com.supwithmice.diary.core

import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.models.Diary
import com.supwithmice.diary.models.Student
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

suspend fun getAttachment(attachmentId: Int){
    val response = client.get(url + "attachments/$attachmentId")
    TODO("save attachment")
}

suspend fun getDiary(student: Student, yearId: Int, start: String? = null, end: String? = null): Diary {

    val start = start ?: getFirstDay()
    val end = end ?: getLastDay()

    val diary: Diary = client.submitForm(
        url + "student/diary",
        formParameters = Parameters.build {
            append("studentId", student.studentId.toString())
            append("yearId", yearId.toString())
            append("weekStart", start)
            append("weekEnd", end)
        }
    ).body()

    return diary
}

suspend fun getOverdue(student: Student, yearId: Int, start: String? = null, end: String? = null) {

    val start = start ?: getFirstDay()
    val end = end ?: getLastDay()

    val overdue: Diary = client.submitForm(
        url + "student/diary/pastMandatory",
        formParameters = Parameters.build {
            append("studentId", student.studentId.toString())
            append("yearId", yearId.toString())
            append("weekStart", start)
            append("weekEnd", end)
        }
    ).body()
}