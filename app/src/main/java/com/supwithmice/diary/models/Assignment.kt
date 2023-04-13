package com.supwithmice.diary.models

data class Assignment(
    val assignmentName: String,
    val classMeetingId: Int,
    val dueDate: String,
    val id: Int,
    val mark: Mark,
    val typeId: Int,
    val weight: Int
)