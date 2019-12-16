package com.example.timetable.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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

        val adapter = TimetableLessonAdapter(TimetableLessonListener { timetableId ->
            Toast.makeText(context, "${timetableId}", Toast.LENGTH_LONG).show() })

        binding.timetableViewModel = timetableViewModel
        binding.setLifecycleOwner(this)
        binding.lessonList.adapter = adapter

        timetableViewModel.timetableEntries.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        timetableViewModel.navigateToLesson.observe(this, Observer { timetable ->
            timetable?.let {
                this.findNavController().navigate(
                    TimetableFragmentDirections
                        .actionTimeTableFragmentToLessonFragment(timetable.id))
                timetableViewModel.doneNavigating()
            }
        })

        timetableViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()

                timetableViewModel.doneShowingSnackbar()
            }
        })

        return binding.root
    }
}