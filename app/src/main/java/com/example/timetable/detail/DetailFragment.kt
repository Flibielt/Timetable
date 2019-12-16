package com.example.timetable.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.timetable.R
import com.example.timetable.database.TimetableDatabase
import com.example.timetable.databinding.LessonDetailFragmentBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LessonDetailFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.lesson_detail_fragment, container, false)
        val application = requireNotNull(this.activity).application

        val lessonDatabase = TimetableDatabase.getInstance(application).lessonDao
        val timetableDatabase = TimetableDatabase.getInstance(application).timetableDao

        val arguments = DetailFragmentArgs.fromBundle(arguments)

        val viewModelFactory = DetailViewModelFactory(arguments.timetableKey, lessonDatabase, timetableDatabase, application)
        val detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.detailViewModel = detailViewModel

        binding.setLifecycleOwner(this)

        detailViewModel.navigateToTimetable.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(DetailFragmentDirections.actionLessonDetailToTimeTableFragment())
                detailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}