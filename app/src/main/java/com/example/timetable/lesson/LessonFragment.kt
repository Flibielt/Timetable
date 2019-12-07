package com.example.timetable.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.R
import com.example.timetable.database.TimetableDatabase
import com.example.timetable.databinding.LessonFragmentBinding

class LessonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LessonFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.lesson_fragment, container, false)
        val application = requireNotNull(this.activity).application

        val lessonDataSource = TimetableDatabase.getInstance(application).lessonDao
        val timetableDataSource = TimetableDatabase.getInstance(application).timetableDao
        val viewModelFactory = LessonViewModelFactory(lessonDataSource, timetableDataSource, application)

        val lessonViewModel = ViewModelProviders.of(this, viewModelFactory).get(LessonViewModel::class.java)
        binding.lessonViewModel = lessonViewModel

        //todo: Show snackbar (optional)
        //todo: navigation

        return binding.root
    }
}
