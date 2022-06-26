package com.tasks.discogsconsumer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tasks.discogsconsumer.R
import com.tasks.discogsconsumer.domain.model.Release

class ReleaseAdapter(diffCallback: DiffUtil.ItemCallback<Release>, private val clickCallback: (Int) -> Unit):
PagingDataAdapter<Release, ReleaseAdapter.ReleaseViewHolder>(diffCallback) {

    inner class ReleaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTv: TextView = itemView.findViewById(R.id.title_value_tv)
        private val artistTv: TextView = itemView.findViewById(R.id.artist_value_tv)
        private val yearTv: TextView = itemView.findViewById(R.id.year_value_tv)

        fun bind(release: Release?) {
            titleTv.text = release?.title
            artistTv.text = release?.artist
            yearTv.text = release?.year.toString()
            val id = release?.id ?: 0
            itemView.setOnClickListener { clickCallback(id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.release_list_item,
            parent, false)
        return ReleaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReleaseViewHolder, position: Int) {
        val release = getItem(position)
        holder.bind(release)
    }

    object ReleaseComparator : DiffUtil.ItemCallback<Release>() {
        override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean {
            return oldItem == newItem
        }
    }
}