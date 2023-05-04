package com.supwithmice.diary.models

data class DiarySettings(
    val birthDate: String,
    var email: String,
    val existsPhoto: Boolean,
    val firstName: String,
    val lastName: String,
    val loginName: String,
    val middleName: String,
    var mobilePhone: String,
    val preferedCom: String,
    val roles: List<String>,
    val schoolyearId: Int,
    val userId: Int,
    val userSettings: UserSettings,
    val windowsAccount: String
)