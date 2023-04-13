package com.supwithmice.diary.utils

import com.supwithmice.diary.client
import com.supwithmice.diary.getFirstDay
import com.supwithmice.diary.getLastDay
import com.supwithmice.diary.models.Diary
import com.supwithmice.diary.models.Student
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

val url = "http://109.195.102.150/webapi/"

suspend fun getAttachment(attachmentId: Int, ){
    val response = client.get(url + "attachments/$attachmentId")
    TODO("save attachment")
}

suspend fun getDiary(student: Student, yearId: Int, start: String? = null, end: String? = null){

    if (start == null) {
        val start = getFirstDay()
    }
    if (end == null) {
        val end = getLastDay()
    }

    val diary: Diary = client.submitForm(
        url + "student/diary",
        formParameters = Parameters.build {
            append("studentId", student.studentId.toString())
            append("yearId", yearId.toString())
            append("weekStart", start.toString())
            append("weekEnd", end.toString())
        }
    ).body()

}