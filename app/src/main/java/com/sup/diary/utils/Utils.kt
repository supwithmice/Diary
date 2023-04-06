package com.sup.diary

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.*
import java.text.SimpleDateFormat
import java.util.*

val monday = Calendar.MONDAY
val friday = Calendar.FRIDAY
val calendar = Calendar.getInstance()
var formatter = SimpleDateFormat("yyyy-MM-dd")

var client = HttpClient(OkHttp) {
    followRedirects = false
    install(HttpCookies)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    install(ContentNegotiation) { gson() }
}

fun getFirstDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, monday)
    val date = calendar.time
    return formatter.format(date)
}

fun getLastDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, friday)
    val date = calendar.time
    return formatter.format(date)
}

fun Any?.log(tag: String = "LOG") = Log.d(tag, this.toString())
