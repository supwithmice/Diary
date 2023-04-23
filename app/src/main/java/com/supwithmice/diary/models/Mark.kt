package com.supwithmice.diary.models

data class Mark(
    val assignmentId: Int,
    val dutyMark: Boolean,
    val mark: Int?,
    val resultScore: Any,
    val studentId: Int
)