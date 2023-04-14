package com.supwithmice.diary.core

import android.content.Context
import android.content.SharedPreferences

object SettingsModule {
    private lateinit var settings: SharedPreferences

    fun Context.initSettings() {
        settings = this.getSharedPreferences("supwithmiceDiary", Context.MODE_PRIVATE)
    }

    private fun checkIsInitialized() {
        if (!::settings.isInitialized) {
            throw Exception("SettingsModule is not initialized")
        }
    }

    var diaryUsername: String?
        get() {
            checkIsInitialized()
            return settings.getString("diaryUsername", null)
        }
        set(value) {
            checkIsInitialized()
            settings.edit().putString("diaryUsername", value).apply()
        }

    var diaryPassword: String?
        get() {
            checkIsInitialized()
            return settings.getString("diaryPassword", null)
        }
        set(value) {
            checkIsInitialized()
            settings.edit().putString("diaryPassword", value).apply()
        }
}