package com.supwithmice.diary.utils

import android.util.Log
import com.supwithmice.diary.core.AuthModule.url
import com.supwithmice.diary.core.client
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Any?.log(tag: String = "log") = Log.d(tag, this.toString())

fun outLogger() = CoroutineScope(Dispatchers.IO).launch {
    client.post(url + "auth/logout")
}