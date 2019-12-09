package com.example.timetable.lesson

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.Lesson
import com.example.timetable.database.LessonDao
import com.example.timetable.database.TimetableDao
import kotlinx.coroutines.*

class LessonViewModel (
    private val timetableEntryKey: Long = 0L,
    val lessonDatabase: LessonDao,
    val timetableDatabase: TimetableDao,
    application: Application
    ) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var lessonName: String = "Math"
    var lessonPosition: Int = 1

    private var lesson = MutableLiveData<Lesson?>()
    private val lessons = lessonDatabase.getAllLessons()

    private val _navigateToTimetable = MutableLiveData<Boolean?>()
    val navigateToTimetable: LiveData<Boolean?>
        get() = _navigateToTimetable

    fun doneNavigating() {
        _navigateToTimetable.value = null
    }

    private fun onSetLessonName(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val lesson = Lesson()
                lesson.name = name
                lessonDatabase.insert(lesson)
                val timetableEntry = timetableDatabase.get(timetableEntryKey)
                //todo nullPointerException
                timetableEntry!!.lessonId = lesson.id
                timetableDatabase.update(timetableEntry)
            }
        }
    }

    private fun onSetLessonPosition(position: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val timetableEntry= timetableDatabase.get(timetableEntryKey) ?:return@withContext
                timetableEntry.position = position
                timetableDatabase.update(timetableEntry)
            }
        }
    }

    fun onUpdateLesson() {
        onSetLessonName(lessonName)
        onSetLessonPosition(lessonPosition)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}