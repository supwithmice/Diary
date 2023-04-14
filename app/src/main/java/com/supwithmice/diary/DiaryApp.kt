package com.supwithmice.diary

import android.app.Application
import android.widget.Toast
import com.supwithmice.diary.core.SettingsModule.initSettings

class DiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        initSettings()
    }

    companion object {
        fun appToast(message: String) {
            Toast.makeText(instance, message, Toast.LENGTH_SHORT).show()
        }

        lateinit var instance: DiaryApp
            private set
    }
}