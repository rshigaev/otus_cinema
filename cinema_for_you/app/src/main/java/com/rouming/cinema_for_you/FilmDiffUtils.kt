package com.rouming.cinema_for_you

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.rouming.cinema_for_you.MainActivity.Companion.TAG

class FilmDiffUtils(private val oldList: MutableList<Film>, private val newList: MutableList<Film>):DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        Log.d(TAG,"old = ${oldItem.like}, new = ${newItem.like}")
        return oldItem.like == newItem.like && oldItem.isTouched == newItem.isTouched
    }
}