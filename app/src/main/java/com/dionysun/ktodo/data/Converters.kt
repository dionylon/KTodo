package com.dionysun.ktodo.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun calenderToTimestamp(date: Calendar): Long  = date.time.time

    @TypeConverter
    fun timestampToCalender(timestamp: Long): Calendar{
        val calender = Calendar.getInstance()
        calender.timeInMillis = timestamp
        return calender
    }
}