package com.rouming.cinema_for_you.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.viewHolder.FilmItemViewHolder
import com.rouming.cinema_for_you.R

class FilmPagerAdapter(private val listener: FilmItemListener) : PagingDataAdapter<Film, FilmItemViewHolder>(
    FilmComparator
) {

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        val item = getItem(position)!!
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return FilmItemViewHolder(layout.inflate(R.layout.item_film,parent, false))
    }

    interface FilmItemListener{
        fun onClickItem(item: Film, position: Int)
        fun onClickCheckBoxItem(view: View, item: Film, isChecked:Boolean)
        fun onClickReminder(view: View, item: Film)
    }

    object FilmComparator : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
}
