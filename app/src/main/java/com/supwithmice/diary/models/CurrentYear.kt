package com.supwithmice.diary.models

data class CurrentYear(
    val archiveStatus: ArchiveStatus,
    val endDate: String,
    val globalYearId: Int,
    val id: Int,
    val name: String,
    val schoolId: Int,
    val startDate: String,
    val status: String,
    val weekEndSet: Int
)