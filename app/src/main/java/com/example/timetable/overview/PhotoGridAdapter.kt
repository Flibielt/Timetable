package com.example.timetable.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.databinding.GridViewItemBinding
import com.example.timetable.network.PlaceholderProperty

class PhotoGridAdapter : ListAdapter<PlaceholderProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlaceholderProperty>() {
        override fun areItemsTheSame(oldItem: PlaceholderProperty, newItem: PlaceholderProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlaceholderProperty, newItem: PlaceholderProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class MarsPropertyViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeholderProperty: PlaceholderProperty) {
            binding.property = placeholderProperty
            binding.executePendingBindings()
        }
    }
}