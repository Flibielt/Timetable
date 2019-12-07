package com.example.timetable.timetable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.LessonDao
import com.example.timetable.database.Timetable
import com.example.timetable.database.TimetableDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class TimetableViewModel (
    val database: TimetableDao,
    val lessonDao: LessonDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var lesson = MutableLiveData<Timetable?>()
    private val lessons = database.getAllLessons()

    fun getLessonName(lessonId: Long): String? {
        return lessonDao.get(lessonId)?.name
    }
}