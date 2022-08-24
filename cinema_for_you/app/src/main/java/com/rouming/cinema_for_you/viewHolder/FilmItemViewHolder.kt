package com.rouming.cinema_for_you.viewHolder

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.R
import com.rouming.cinema_for_you.adapter.FilmPagerAdapter

open class FilmItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val label:TextView = itemView.findViewById(R.id.item_tvLabel)
    val img: ImageView = itemView.findViewById(R.id.item_img)
    val button: Button = itemView.findViewById(R.id.item_btnDetails)
    val checkBox: CheckBox = itemView.findViewById(R.id.item_cbLike)
    val layout: ConstraintLayout = itemView.findViewById(R.id.item_layout)
    val fabReminder: FloatingActionButton = itemView.findViewById(R.id.fab_add_notification)

    fun bind(item: Film, listener: FilmPagerAdapter.FilmItemListener){

        if(item != null && itemView!= null){
            if(item.coverUrl != null)
            {
                Glide.with(img)
                    .load(item.coverUrl)
                    .placeholder(R.drawable.ic_load_image)
                    .error(R.drawable.ic_error_image)
                    .into(img)
            } else
                img.setImageResource(R.drawable.ic_error_image)

            label.text = item.nameRu ?: (item.nameEn ?: "")
            checkBox.isChecked = item.like
            button.setOnClickListener {
                listener.onClickItem(item,adapterPosition)
            }

            checkBox.setOnClickListener {
                listener.onClickCheckBoxItem(itemView, item, checkBox.isChecked)
            }

            fabReminder.setOnClickListener {
                listener.onClickReminder(itemView, item)
            }

            if(item.isTouched){
                layout.setBackgroundResource(R.color.light_grey)
            } else {
            layout.setBackgroundResource(R.color.white)
            }
        }
    }
}