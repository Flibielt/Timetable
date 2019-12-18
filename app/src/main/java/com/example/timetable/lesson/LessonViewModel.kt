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

    private val _lesson = MutableLiveData<String>()
    val lessonName: LiveData<String>
        get() = _lesson

    private val _day = MutableLiveData<String>()
    val lessonDay: LiveData<String>
        get() = _day

    private val _navigateToTimetable = MutableLiveData<Boolean?>()
    val navigateToTimetable: LiveData<Boolean?>
        get() = _navigateToTimetable

    init {
        _lesson.value = "Math"
        _day.value = "Friday"
    }

    fun doneNavigating() {
        _navigateToTimetable.value = null
    }

    fun setLessonsName(id: Int) {
        if (id == 0) {
            _lesson.value = "History"
        } else if (id == 1) {
            _lesson.value = "Informatics"
        } else if (id == 2) {
            _lesson.value = "Physics"
        } else if (id == 3) {
            _lesson.value = "Chemistry"
        } else if (id == 4) {
            _lesson.value = "Literature"
        } else {
            _lesson.value = "Math"
        }
        print("Chosen lesson: ")
        println(_lesson.value)
    }

    fun setLessonsDay(id: Int) {
        if (id == 0) {
            _day.value = "Monday"
        } else if (id == 1) {
            _day.value = "Tuesday"
        } else if (id == 2) {
            _day.value = "Wednesday"
        } else if (id == 3) {
            _day.value = "Thursday"
        } else {
            _day.value = "Friday"
        }
        print("Chosen day: ")
        println(_day.value)
    }

    private fun onSetLessonName(name: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val lesson = Lesson()
                lesson.name = name
                //insert(lesson)
                val id: Long
                if (name == "History") {
                    id = 0L
                } else if (name == "Informatics") {
                    id = 1L
                } else if (name == "Physics") {
                    id = 2L
                } else if (name == "Chemistry") {
                    id = 3L
                } else if (name == "Literature") {
                    id = 4L
                } else {
                    id = 5L
                }

                lesson.id = id

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
        onSetLessonName(_lesson.value!!)
        onSetLessonDay(_day.value!!)
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