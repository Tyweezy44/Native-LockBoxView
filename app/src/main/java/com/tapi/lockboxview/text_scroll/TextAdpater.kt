package com.tapi.lockboxview.text_scroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tapi.lockboxview.R

class TextAdapter : ListAdapter<String, TextHolder>(TextDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.text_holder, parent, false)
        return TextHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class TextDiff : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}