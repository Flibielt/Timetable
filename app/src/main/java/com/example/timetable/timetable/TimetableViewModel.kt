package com.example.timetable.timetable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.timetable.database.Lesson
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
    val timetableEntries = database.getAllLessons()
    private lateinit var lessons: List<Lesson>
    private val _navigateToLesson = MutableLiveData<Timetable>()
    private val _navigateToDetail = MutableLiveData<Long>()
    private val _navigateToOverview = MutableLiveData<Boolean?>()

    val timetableString = Transformations.map(timetableEntries) {timetable ->
        formatLessons(timetable, getLessons(), application.resources)
    }

    val navigateToLesson: LiveData<Timetable>
        get() = _navigateToLesson

    val navigateToDetail: LiveData<Long>
        get() = _navigateToDetail

    val navigateToOverview: LiveData<Boolean?>
        get() = _navigateToOverview

    fun getLessonName(lessonId: Long): String? {
        return lessonDao.get(lessonId)?.name
    }

    fun doneNavigating() {
        _navigateToLesson.value = null
    }

    fun getLessons(): List<Lesson>? {
        if (!this::lessons.isInitialized) {
            return null
        }
        return lessons
    }

    private suspend fun getLessonsList(): List<Lesson> {
        return withContext(Dispatchers.IO) {
            val lessons = lessonDao.getEveryLesson()
            lessons
        }

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
        initializeTimetable()
    }

    private fun initializeTimetable() {
        uiScope.launch {
            timetableEntry.value = getLastAddedTimetable()
        }
    }

    fun onAddNewLesson() {
        uiScope.launch {
            val newTimetableEntry = Timetable()
            newTimetableEntry.day = "Monday"
            insert(newTimetableEntry)
            _navigateToLesson.value = getLastAddedTimetable()
        }
    }

    fun onGetInternetData() {
        uiScope.launch {
            _navigateToOverview.value = true
        }
    }

    private suspend fun getLastAddedTimetable(): Timetable? {
        return withContext(Dispatchers.IO) {
            var timetable = database.getLastAddedLesson()
            lessons = getLessonsList()
            if (timetable?.day == "Someday") {
                timetable = null
            }
            timetable
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
            lessonDao.clear()
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onTimetableClicked(id: Long) {
        _navigateToDetail.value = id
    }

    fun onLessonDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun onOverviewNavigated() {
        _navigateToOverview.value = null
    }
}