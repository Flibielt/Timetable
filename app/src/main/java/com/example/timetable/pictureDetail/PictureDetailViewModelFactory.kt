package com.example.timetable.pictureDetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.network.PlaceholderProperty

class PictureDetailViewModelFactory(
    private val placeholderProperty: PlaceholderProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PictureDetailViewModel::class.java)) {
            return PictureDetailViewModel(placeholderProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
