package com.example.timetable.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.database.LessonDao
import com.example.timetable.database.TimetableDao
import java.lang.IllegalArgumentException

class DetailViewModelFactory (
    private val timetableEntryKey: Long = 0L,
    private val lessonDataSource: LessonDao,
    private val timetableDataSource: TimetableDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(timetableEntryKey, lessonDataSource, timetableDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}