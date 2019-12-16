package com.example.timetable.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.database.Timetable
import com.example.timetable.databinding.ListItemTimetableLessonBinding

class TimetableLessonAdapter : ListAdapter<Timetable, TimetableLessonAdapter.ViewHolder>(TimetableLessonDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemTimetableLessonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Timetable) {
            binding.timetable = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTimetableLessonBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TimetableLessonDiffCallback  : DiffUtil.ItemCallback<Timetable>() {
    override fun areItemsTheSame(oldItem: Timetable, newItem: Timetable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Timetable, newItem: Timetable): Boolean {
        return oldItem == newItem
    }

}