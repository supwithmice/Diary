package com.supwithmice.diary.models

data class DiaryInit(
    val currentStudentId: Int,
    val externalUrl: String,
    val maxMark: Int,
    val newDiskToken: String,
    val newDiskWasRequest: Boolean,
    val students: List<Student>,
    val ttsuRl: String,
    val weekStart: String,
    val weight: Boolean,
    val withLaAssigns: Boolean,
    val yaClass: Boolean,
    val yaClassAuthUrl: String
)