package com.example.coinsliberty.utils

import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE_MONTH = "dd MMM yyy"

fun isSameDay(date1: Long, date2: Long): Boolean {
    val cal1 = Calendar.getInstance();
    val cal2 = Calendar.getInstance();
    cal1.time = Date(date1)
    cal2.time = Date(date2)
    return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
}

fun convertTimestampForUI(time: Long?, format: String = FORMAT_DATE_MONTH): String? {
    if (time == null) return null

    val date = Date(time)
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())

    return simpleDateFormat.format(date)
}