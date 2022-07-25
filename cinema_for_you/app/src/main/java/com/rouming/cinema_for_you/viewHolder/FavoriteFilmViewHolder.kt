package com.rouming.cinema_for_you.viewHolder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.R

class FavoriteFilmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val label: TextView = itemView.findViewById(R.id.item_fav_tv)
    private val img: ImageView = itemView.findViewById(R.id.item_fav_img)

    fun bind(item: Film){
        label.text = item.nameRu

        Glide.with(img)
            .load(item.coverUrl)
            .placeholder(R.drawable.ic_load_image)
            .error(R.drawable.ic_error_image)
            .into(img)

        Log.d("OTUS", "биндим айтем   ${item}")
    }
}