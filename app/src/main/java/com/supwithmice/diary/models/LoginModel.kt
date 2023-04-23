package com.supwithmice.diary.models

data class LoginModel(
    val accessToken: String,
    val accountInfo: AccountInfo,
    val at: String,
    val code: Any,
    val entryPoint: String,
    val errorMessage: Any,
    val refreshToken: String,
    val requestData: RequestData,
    val timeOut: Int,
    val tokenType: String,
    val message: String
)