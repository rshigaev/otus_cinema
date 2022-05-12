package com.rouming.cinema_for_you

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView

class FilmItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val label:TextView = itemView.findViewById(R.id.item_tvLabel)
    private val img: ImageView = itemView.findViewById(R.id.item_img)
    private val button: Button = itemView.findViewById(R.id.item_btnDetails)
    private val checkBox: CheckBox = itemView.findViewById(R.id.item_cbLike)
    private val layout: ConstraintLayout = itemView.findViewById(R.id.item_layout)

    fun bind(item:FilmItem, listener:FilmAdapter.FilmItemListener){
        label.text = item.label
        img.setImageResource(item.image)
        checkBox.isSelected = item.like
        button.setOnClickListener {
            listener.onClickItem(item,adapterPosition)
        }

        checkBox.setOnClickListener {
            listener.onClickCheckBoxItem(item, checkBox.isChecked)
        }
        if(item.isTouched){
            layout.setBackgroundResource(R.color.light_grey)
        } else {
            layout.setBackgroundResource(R.color.white)
        }
    }
}