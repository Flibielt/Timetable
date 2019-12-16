package com.example.timetable.timetable

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.timetable.database.Timetable

@BindingAdapter("lessonDay")
fun TextView.setLessonDay(item: Timetable?) {
    item?.let {
        text = item.day
    }
}

@BindingAdapter("lessonName")
fun TextView.setLessonName(item: Timetable?) {
    item?.let {
        if (item.lessonId == 5L) {
            text = "Math"
        } else if (item.lessonId == 4L) {
            text = "Literature"
        } else if (item.lessonId == 3L) {
            text = "Chemistry"
        } else if (item.lessonId == 2L) {
            text = "Physics"
        } else if (item.lessonId == 1L) {
            text = "Informatics"
        } else {
            text = "History"
        }
    }
}