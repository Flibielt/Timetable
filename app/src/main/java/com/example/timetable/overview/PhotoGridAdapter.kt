package com.example.timetable.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetable.databinding.GridViewItemBinding
import com.example.timetable.network.PlaceholderProperty

class PhotoGridAdapter (private val onClickListener: OnClickListener) : ListAdapter<PlaceholderProperty, PhotoGridAdapter.PlaceholderPropertyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.PlaceholderPropertyViewHolder {
        return PlaceholderPropertyViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PlaceholderPropertyViewHolder, position: Int) {
        val placeholderProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(placeholderProperty)
        }
        holder.bind(placeholderProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlaceholderProperty>() {
        override fun areItemsTheSame(oldItem: PlaceholderProperty, newItem: PlaceholderProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlaceholderProperty, newItem: PlaceholderProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class PlaceholderPropertyViewHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(placeholderProperty: PlaceholderProperty) {
            binding.property = placeholderProperty
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (placeholderProperty: PlaceholderProperty) -> Unit) {
        fun onClick(placeholderProperty: PlaceholderProperty) = clickListener(placeholderProperty)
    }
}