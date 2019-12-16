package com.example.timetable.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.R
import com.example.timetable.TextItemViewHolder
import com.example.timetable.database.Timetable

class TimetableLessonAdapter : RecyclerView.Adapter<TimetableLessonAdapter.ViewHolder>() {

    var data = listOf<Timetable>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonName: TextView = itemView.findViewById(R.id.lesson_name)
        val lessonDay: TextView = itemView.findViewById(R.id.lesson_day)

        fun bind(item: Timetable) {
            val res = itemView.context.resources
            lessonDay.text = item.day
            lessonName.text = item.lessonId.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_timetable_lesson, parent, false)
                return ViewHolder(view)
            }
        }
    }
}