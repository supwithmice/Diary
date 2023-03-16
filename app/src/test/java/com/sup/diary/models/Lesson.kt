package com.sup.diary.models

data class Lesson(
    val assignments: List<Assignment>,
    val classmeetingId: Int,
    val day: String,
    val endTime: String,
    val isEaLesson: Boolean,
    val number: Int,
    val relay: Int,
    val room: Any,
    val startTime: String,
    val subjectName: String
)