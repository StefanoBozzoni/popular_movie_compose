package com.example.mycomposem3playground.data.local.database

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    /* requires SDK min 26
    @TypeConverter
    fun timeToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }
   */

}