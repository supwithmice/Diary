package com.supwithmice.diary.core

import java.text.SimpleDateFormat
import java.util.*

const val monday = Calendar.MONDAY
const val friday = Calendar.FRIDAY
val calendar: Calendar = Calendar.getInstance()
var initialWeek = calendar.get(Calendar.WEEK_OF_YEAR)
var formatter = SimpleDateFormat("yyyy-MM-dd")
var weekOffset = 0

fun getFirstDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, monday)
    return calendar.returnFormatted()
}

fun getLastDay(): String {
    calendar.set(Calendar.DAY_OF_WEEK, friday)
    return calendar.returnFormatted()
}

fun setWeek() {
    val offset = weekOffset + initialWeek

    when {
        offset < 0 -> {
            weekOffset = 0
            initialWeek = 53
            calendar.set(Calendar.WEEK_OF_YEAR, initialWeek)
        }
        offset > 53 -> {
            weekOffset = 0
            initialWeek = 1
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
            calendar.set(Calendar.WEEK_OF_YEAR, initialWeek)
        }
        else -> {
            calendar.set(Calendar.WEEK_OF_YEAR, offset)
        }
    }
}

fun Calendar.returnFormatted(): String = formatter.format(this.time)