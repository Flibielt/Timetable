package com.example.timetable.timetable

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.database.LessonDao
import com.example.timetable.database.TimetableDao
import java.lang.IllegalArgumentException

class TimetableViewModelFactory (
    private val timetableDatasource: TimetableDao,
    private val lessonDatasource: LessonDao,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimetableViewModel::class.java)) {
            return TimetableViewModel(timetableDatasource, lessonDatasource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}