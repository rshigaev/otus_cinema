package com.rouming.cinema_for_you

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val FILM_ITEM_VIEWTYPE = 1

class FilmAdapter(private val itemList:MutableList<FilmItem>, private val listener:FilmItemClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var lst:MutableList<FilmItem> = itemList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return when (viewType){
            FILM_ITEM_VIEWTYPE -> FilmItemViewHolder(layout.inflate(R.layout.item_film,parent, false))
            else -> FilmItemViewHolder(layout.inflate(R.layout.item_film,parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return FILM_ITEM_VIEWTYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmItemViewHolder -> holder.bind(lst[position], listener)
        }

    }

    override fun getItemCount(): Int {
        return lst.size
    }

    interface FilmItemClickListener{
        fun onClickItem(item:FilmItem,  position: Int)
        fun onClickCheckBoxItem(item:FilmItem,  position: Int)
    }
}