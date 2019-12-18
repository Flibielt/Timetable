package com.example.timetable.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetable.network.MarsApi
import com.example.timetable.network.PlaceholderProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class InternetDataStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _status = MutableLiveData<InternetDataStatus>()
    val status: LiveData<InternetDataStatus>
        get() = _status

    private val _properties = MutableLiveData<List<PlaceholderProperty>>()
    val properties: LiveData<List<PlaceholderProperty>>
        get() = _properties

    /**
     * Call getInternetDataProperties() on init so we can display status immediately.
     */
    init {
        getInternetDataProperties()
    }

    /**
     * Sets the value of the status LiveData to the Internet Data API status.
     */
    private fun getInternetDataProperties() {
        coroutineScope.launch {
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties()
            try {
                _status.value = InternetDataStatus.LOADING
                var listResult = getPropertiesDeferred.await()
                _status.value = InternetDataStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = InternetDataStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}