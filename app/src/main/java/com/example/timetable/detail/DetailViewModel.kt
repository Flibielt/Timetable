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
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val timetableEntry: LiveData<Timetable>
    private val lesson: LiveData<Lesson>

    fun getTimetable() = timetableEntry
    fun getLesson() = lesson

    val lessonDay: String
    val lessonName: String

    init {
        timetableEntry = timetableDatabase.getLiveData(timetableEntryKey)
        lesson = lessonDatabase.getLiveData(timetableEntry.value!!.lessonId)
        lessonDay = timetableEntry.value!!.day
        lessonName = lesson.value!!.name
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