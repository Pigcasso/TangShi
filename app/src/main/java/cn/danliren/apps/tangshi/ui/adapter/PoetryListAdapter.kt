package cn.danliren.apps.tangshi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.danliren.apps.tangshi.R
import cn.danliren.apps.tangshi.data.Poetry

class PoetryListAdapter(
    private val items: List<Poetry>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<PoetryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoetryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poetry, parent, false)
        return PoetryViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: PoetryViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class PoetryViewHolder(
    itemView: View,
    onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.item_title)

    init {
        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun onBind(item: Poetry) {
        title.text = item.title
    }
}