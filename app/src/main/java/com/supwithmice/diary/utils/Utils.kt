package com.supwithmice.diary.utils

import android.util.Log

fun Any?.log(tag: String = "log") = Log.d(tag, this.toString())
