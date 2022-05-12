package com.rouming.cinema_for_you

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class FavoriteFilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val label: TextView = itemView.findViewById(R.id.item_fav_tv)
    private val img: ImageView = itemView.findViewById(R.id.item_fav_img)

    fun bind(item:FilmItem, listener:FilmAdapter.FilmItemListener){
        label.text = item.label
        img.setImageResource(item.image)
        label.text = item.label

        Log.d("OTUS", "биндим айтем   ${item}")
    }
}