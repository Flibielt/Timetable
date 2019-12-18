package com.example.timetable.pictureDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timetable.databinding.FragmentDetailBinding

class PictureDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val placeholderProperty = PictureDetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = PictureDetailViewModelFactory(placeholderProperty, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(PictureDetailViewModel::class.java)

        return binding.root
    }
}