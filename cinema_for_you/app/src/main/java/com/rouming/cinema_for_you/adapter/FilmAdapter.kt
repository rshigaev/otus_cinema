package com.rouming.cinema_for_you.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.*
import com.rouming.cinema_for_you.viewHolder.FavoriteFilmViewHolder

private const val FAVORITE_FILM_ITEM_VIEWTYPE = 2

class FilmAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {
        var lst = arrayListOf<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return FavoriteFilmViewHolder(layout.inflate(R.layout.item_favorite_film,parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return FAVORITE_FILM_ITEM_VIEWTYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FavoriteFilmViewHolder -> holder.bind(lst[position])
        }
    }

    override fun getItemCount(): Int = lst.size

    fun getList():ArrayList<Film> = lst

    fun getFilmById(pos:Int) = lst[pos]

    fun setData(itemList:ArrayList<Film>) {
        lst = itemList.map{ it.copy() } as ArrayList<Film>
    }

    fun updateItem(updatedLst: ArrayList<Film>) {
        val diffCallback = FilmDiffUtils(getList(), updatedLst)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        setData(updatedLst)
        diffResult.dispatchUpdatesTo(this)
    }
}