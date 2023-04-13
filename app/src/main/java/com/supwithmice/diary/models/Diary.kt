package com.supwithmice.diary.models

data class Diary(
    val className: String,
    val laAssigns: List<Any>,
    val termName: String,
    val weekDays: List<WeekDay>,
    val weekEnd: String,
    val weekStart: String
)