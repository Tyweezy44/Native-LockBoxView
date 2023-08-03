package com.tapi.lockboxview.text_scroll

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.tapi.lockboxview.R

class TextHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var textView: AppCompatTextView? = null
    fun bind(content: String) {
        textView = itemView.findViewById(R.id.content)
        textView?.text = content
    }

}