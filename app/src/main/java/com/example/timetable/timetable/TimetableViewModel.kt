package com.example.timetable.timetable

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetable.database.LessonDao
import com.example.timetable.database.Timetable
import com.example.timetable.database.TimetableDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

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
}