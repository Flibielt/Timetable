package com.example.timetable.lesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.Lesson
import com.example.timetable.database.LessonDao
import com.example.timetable.database.TimetableDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class LessonViewModel (
    val lessonDatabase: LessonDao,
    val timetableDatabase: TimetableDao,
    application: Application
    ) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var lesson = MutableLiveData<Lesson?>()
    private val lessons = lessonDatabase.getAllLessons()
}