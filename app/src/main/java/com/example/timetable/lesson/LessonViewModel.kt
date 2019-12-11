package com.example.timetable.lesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.Lesson
import com.example.timetable.database.LessonDao
import com.example.timetable.database.Timetable
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
    var lessonDay: String = "Monday"

    private var lesson = MutableLiveData<Lesson?>()
    private val lessons = lessonDatabase.getAllLessons()

    private val _navigateToTimetable = MutableLiveData<Boolean?>()
    val navigateToTimetable: LiveData<Boolean?>
        get() = _navigateToTimetable

    fun doneNavigating() {
        _navigateToTimetable.value = null
    }

    fun setLessonsName(id: Int) {
        if (id == 0) {
            lessonName = "History"
        } else if (id == 1) {
            lessonName = "Informatics"
        } else if (id == 2) {
            lessonName = "Physics"
        } else if (id == 3) {
            lessonName = "Chemistry"
        } else if (id == 4) {
            lessonName = "Literature"
        } else {
            lessonName = "Math"
        }
    }

    fun setLessonsDay(id: Int) {
        if (id == 0) {
            lessonDay = "Monday"
        } else if (id == 1) {
            lessonDay = "Tuesday"
        } else if (id == 2) {
            lessonDay = "Wednesday"
        } else if (id == 3) {
            lessonDay = "Thursday"
        } else {
            lessonDay = "Friday"
        }
    }

    private fun onSetLessonName(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val lesson = Lesson()
                lesson.name = name
                insert(lesson)

                lesson.id = getLastAddedLesson()!!.id

                val timetableEntry = getTimetable(timetableEntryKey)
                timetableEntry!!.lessonId = lesson.id
                updateTimetable(timetableEntry)
            }
        }


    }

    private fun onSetLessonDay(day: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val timetableEntry= timetableDatabase.get(timetableEntryKey) ?:return@withContext
                timetableEntry.day = day
                //timetableDatabase.update(timetableEntry)
                updateTimetable(timetableEntry)
            }
        }
    }

    fun onUpdateLesson() {
        onSetLessonName(lessonName)
        onSetLessonDay(lessonDay)
        _navigateToTimetable.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun insert(lesson: Lesson) {
        withContext(Dispatchers.IO) {
            lessonDatabase.insert(lesson)
        }
    }

    private suspend fun getLastAddedLesson(): Lesson? {
        return withContext(Dispatchers.IO) {
            var lesson = lessonDatabase.getLastAddedLesson()
            if (lesson?.name == "") {
                lesson = null
            }
            lesson
        }
    }

    private suspend fun getAllLesson(): List<Lesson>? {
        return withContext(Dispatchers.IO) {
            var lessons = lessonDatabase.getAllLessons()
            lessons.value
        }
    }

    private suspend fun updateTimetable(timetable: Timetable) {
        withContext(Dispatchers.IO) {
            timetableDatabase.update(timetable)
        }
    }

    private suspend fun getTimetable(key: Long): Timetable? {
        return withContext(Dispatchers.IO) {
            val timetable = timetableDatabase.get(key)
            timetable
        }
    }
}