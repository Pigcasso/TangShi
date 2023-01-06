package com.danliren.apps.tangshi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danliren.apps.tangshi.R

class StringListAdapter(
    private val titles: List<String>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<StringViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poetry, parent, false)
        return StringViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.onBind(titles[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

class StringViewHolder(
    itemView: View,
    onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    val title: TextView = itemView.findViewById(R.id.item_title)

    init {
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun onBind(title: String) {
        this.title.text = title
    }
}