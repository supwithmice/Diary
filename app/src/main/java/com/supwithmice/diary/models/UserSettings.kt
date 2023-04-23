package com.supwithmice.diary.models

data class UserSettings(
    val defaultDesktop: Int,
    val favoriteReports: List<Any>,
    val language: String,
    val passwordExpired: Int,
    val recoveryAnswer: String,
    val recoveryQuestion: String,
    val showMobilePhone: Boolean,
    val showNetSchoolApp: Boolean,
    val theme: Int,
    val userId: Int
)