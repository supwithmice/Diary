package com.supwithmice.diary.core

import java.text.SimpleDateFormat
import java.util.*

const val monday = Calendar.MONDAY
const val friday = Calendar.FRIDAY
val calendar: Calendar = Calendar.getInstance()
var formatter = SimpleDateFormat("yyyy-MM-dd")

fun getFirstDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, monday)
    return calendar.returnFormatted()
}

fun getLastDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, friday)
    return calendar.returnFormatted()
}

fun Calendar.returnFormatted(): String = formatter.format(this.time)