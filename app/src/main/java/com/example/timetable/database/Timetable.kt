package com.example.timetable.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable")
data class Timetable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "lesson_id")
    var lessonId: Long = 0L,

    @ColumnInfo(name = "day")
    var day: String = "Monday",

    @ColumnInfo(name = "position")
    var position: Int = 0
)