package com.example.timetable.pictureDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.timetable.network.PlaceholderProperty

class PictureDetailViewModel(placeholderProperty: PlaceholderProperty,
                             app: Application
) : AndroidViewModel(app) {

    // The internal MutableLiveData for the selected property
    private val _selectedProperty = MutableLiveData<PlaceholderProperty>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<PlaceholderProperty>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = placeholderProperty
    }

}
