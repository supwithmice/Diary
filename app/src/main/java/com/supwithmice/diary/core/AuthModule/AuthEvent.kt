package com.supwithmice.diary.core.AuthModule

import com.supwithmice.diary.models.GetLoginModel

sealed class AuthEvent {
    data class FailedAuth(val reason: String) : AuthEvent()
    data class GotAuth(val dc: GetLoginModel) : AuthEvent()
}