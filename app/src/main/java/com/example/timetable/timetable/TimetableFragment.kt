package com.example.timetable.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.R
import com.example.timetable.database.TimetableDatabase
import com.example.timetable.databinding.TimetableFragmentBinding
import com.google.android.material.snackbar.Snackbar

class TimetableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val binding: TimetableFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.timetable_fragment, container,false)

        val application = requireNotNull(this.activity).application

        val timetableDatasource = TimetableDatabase.getInstance(application).timetableDao
        val lessonDatasource = TimetableDatabase.getInstance(application).lessonDao

        val viewModelFactory = TimetableViewModelFactory(timetableDatasource, lessonDatasource, application)
        val timetableViewModel = ViewModelProviders.of(this, viewModelFactory).get(TimetableViewModel::class.java)

        binding.timetableViewModel = timetableViewModel
        binding.setLifecycleOwner(this)

        //todo: navigation

        return binding.root
    }
}