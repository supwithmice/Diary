package com.sup.diary.models

data class GetLoginModel(
    val accessToken: String,
    val accountInfo: AccountInfo,
    val at: String,
    val code: Any,
    val entryPoint: String,
    val errorMessage: Any,
    val refreshToken: String,
    val requestData: RequestData,
    val timeOut: Int,
    val tokenType: String
)