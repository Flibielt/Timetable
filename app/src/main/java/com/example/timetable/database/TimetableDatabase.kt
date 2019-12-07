package com.example.timetable.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Lesson::class, Timetable::class], version = 1, exportSchema = false)
abstract class TimetableDatabase : RoomDatabase() {

    abstract val lessonDao: LessonDao
    abstract val timetableDao: TimetableDao

    companion object {

        @Volatile
        private var INSTINACE: TimetableDatabase? = null

        fun getInstance(context: Context): TimetableDatabase {
            synchronized(this) {
                var instiance = INSTINACE

                if (instiance == null) {
                    instiance = Room.databaseBuilder(
                        context.applicationContext,
                        TimetableDatabase::class.java,
                        "timetable_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTINACE = instiance
                }

                return instiance
            }
        }
    }
}