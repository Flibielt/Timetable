package com.example.timetable.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.Lesson
import com.example.timetable.database.LessonDao
import com.example.timetable.database.Timetable
import com.example.timetable.database.TimetableDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DetailViewModel (
    private val timetableEntryKey: Long = 0L,
    val lessonDatabase: LessonDao,
    val timetableDatabase: TimetableDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val timetableEntry: LiveData<Timetable>

    fun getTimetable() = timetableEntry

    init {
        timetableEntry = timetableDatabase.getLiveData(timetableEntryKey)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToTimetable = MutableLiveData<Boolean?>()
    val navigateToTimetable: LiveData<Boolean?>
        get() = _navigateToTimetable

    fun doneNavigating() {
        _navigateToTimetable.value = null
    }

    fun onClose() {
        _navigateToTimetable.value = true
    }
}