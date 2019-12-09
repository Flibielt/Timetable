package com.example.timetable.lesson

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.database.LessonDao
import com.example.timetable.database.TimetableDao
import java.lang.IllegalArgumentException

class LessonViewModelFactory (
    private val lessonDataSource: LessonDao,
    private val timetableDataSource: TimetableDao,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            return LessonViewModel(lessonDataSource, timetableDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}