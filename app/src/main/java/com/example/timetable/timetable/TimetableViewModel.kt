package com.example.timetable.timetable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.timetable.database.LessonDao
import com.example.timetable.database.Timetable
import com.example.timetable.database.TimetableDao
import com.example.timetable.formatLessons
import kotlinx.coroutines.*

class TimetableViewModel (
    val database: TimetableDao,
    val lessonDao: LessonDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var timetableEntry = MutableLiveData<Timetable?>()
    private val timetableEntries = database.getAllLessons()
    private val lessons = lessonDao.getAllLessons()
    private val _navigateToLesson = MutableLiveData<Timetable>()

    val timetableString = Transformations.map(timetableEntries) {timetable ->
        formatLessons(timetable, lessons.value, application.resources)
    }

    val navigateToLesson: LiveData<Timetable>
        get() = _navigateToLesson

    fun getLessonName(lessonId: Long): String? {
        return lessonDao.get(lessonId)?.name
    }

    fun doneNavigating() {
        _navigateToLesson.value = null
    }

    /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    val clearButtonVisible = Transformations.map(timetableEntries) {
        it?.isNotEmpty()
    }

    /**
     * Call this immediately after calling `show()` on a toast.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            //todo get something from the database
        }
    }

    fun onAddNewLesson() {
        uiScope.launch {
            val newTimetableEntry = Timetable()
            //todo: set day from spinner
            insert(newTimetableEntry)
            _navigateToLesson.value = newTimetableEntry
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            timetableEntry.value = null
            _showSnackbarEvent.value = true
        }
    }


    private suspend fun insert(timetableEntry: Timetable) {
        withContext(Dispatchers.IO) {
            database.insert(timetableEntry)
        }
    }

    private suspend fun update(timetableEntry: Timetable) {
        withContext(Dispatchers.IO) {
            database.update(timetableEntry)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}