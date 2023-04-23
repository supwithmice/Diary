package com.supwithmice.diary.core.AuthModule

import com.supwithmice.diary.models.LoginModel

sealed class AuthEvent {
    data class FailedAuth(val reason: String) : AuthEvent()
    data class GotAuth(val login: LoginModel) : AuthEvent()
}