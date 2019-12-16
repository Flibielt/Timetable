package com.example.timetable.timetable

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.timetable.database.Timetable

@BindingAdapter("lessonDay")
fun TextView.setLessonDay(item: Timetable) {
    text = item.day
}

@BindingAdapter("lessonName")
fun TextView.setLessonName(item: Timetable) {
    if (item.lessonId > 0) {
        text = "Literature"
    }
    text = "Math"
    //todo: get lesson name from LessonDAO
}