package com.example.timetable.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Lesson::class, Timetable::class], version = 2, exportSchema = false)
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
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                getInstance(context).lessonDao.insert(Lesson(5L, "Math"))
                                getInstance(context).lessonDao.insert(Lesson(4L, "Literature"))
                                getInstance(context).lessonDao.insert(Lesson(3L, "Chemistry"))
                                getInstance(context).lessonDao.insert(Lesson(2L, "Physics"))
                                getInstance(context).lessonDao.insert(Lesson(1L, "Informatics"))
                                getInstance(context).lessonDao.insert(Lesson(0L, "History"))
                            }
                        })
                        .build()

                    INSTINACE = instiance
                }

                return instiance
            }
        }
    }
}