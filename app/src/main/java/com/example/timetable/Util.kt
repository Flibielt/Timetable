package com.example.timetable

import android.content.res.Resources
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.timetable.database.Lesson
import com.example.timetable.database.Timetable

fun formatLessons(timetable: List<Timetable>, lessons: List<Lesson>?, resources: Resources) : Spanned {
    val sb = StringBuilder()
    /*print("Timetable entries: " + timetable.size + "\n")
    timetable.forEach {
        print("Timetable ID: ")
        println(it.id)
        print("Day: ")
        println(it.day)
        print("Lesson ID: ")
        println(it.lessonId)
        println()
    }*/
    //print("Lesson list size: ")
    //println(lessons?.size)
    lessons?.forEach {
        print("Lesson ID: ")
        println(it.id)
        print("Lesson name: ")
        println(it.name)
    }
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

