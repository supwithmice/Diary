package com.supwithmice.diary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.supwithmice.diary.DiaryApp.Companion.appToast
import com.supwithmice.diary.core.AuthModule.AuthEvent
import com.supwithmice.diary.core.AuthModule.authMe
import com.supwithmice.diary.core.SettingsModule.diaryPassword
import com.supwithmice.diary.core.SettingsModule.diaryUsername
import com.supwithmice.diary.core.DiaryData.initData
import com.supwithmice.diary.core.recreateClient
import com.supwithmice.diary.utils.outLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (diaryPassword == null || diaryUsername == null) {
            startActivity(Intent(this, InitialLogin::class.java))
            finish()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                when (val authEvent = authMe(diaryPassword!!, diaryUsername!!)) {
                    is AuthEvent.GotAuth -> {
                        val login = authEvent.login
                        val at = login.at
                        val accessToken = login.accessToken
                        val refreshToken = login.refreshToken

                        recreateClient(accessToken, refreshToken)
                        initData()

                        withContext(Dispatchers.Main) {
                            startActivity(Intent(this@MainActivity, MainMenuActivity::class.java))
                            finish()
                        }
                    }
                    is AuthEvent.FailedAuth -> {
                        diaryPassword = null
                        diaryUsername = null
                        withContext(Dispatchers.Main){
                            appToast("Incorrect data")
                            startActivity(Intent(this@MainActivity, InitialLogin::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        outLogger()
    }
}