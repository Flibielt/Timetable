package com.example.timetable

import android.content.res.Resources
import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.database.Lesson
import com.example.timetable.database.Timetable

fun formatLessons(timetable: List<Timetable>, lessons: List<Lesson>?, resources: Resources) : Spanned {
    val sb = StringBuilder()
    if (!lessons.isNullOrEmpty()) {
        sb.apply {
            timetable.forEach {
                sb.append("Lesson: ")
                for (i in 0 until lessons.size) {
                    if (lessons[i].id == it.lessonId) {
                        sb.append(lessons[i].name)
                    }
                }
                sb.append("<br/>")
                sb.append("Day: ")
                sb.append(it.day)
                sb.append("<br/>")
            }
        }
    } else {
        sb.append("Empty database")
    }

    return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)

}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)