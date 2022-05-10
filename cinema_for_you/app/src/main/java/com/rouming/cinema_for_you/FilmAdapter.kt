package com.rouming.cinema_for_you

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

private const val FILM_ITEM_VIEWTYPE = 1
private const val FAVORITE_FILM_ITEM_VIEWTYPE = 2

class FilmAdapter(
    private val listener:FilmItemListener,
    private val type:String = "main")
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var lst = arrayListOf<FilmItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(parent.context)

        return when (viewType){
            FILM_ITEM_VIEWTYPE -> FilmItemViewHolder(layout.inflate(R.layout.item_film,parent, false))
            FAVORITE_FILM_ITEM_VIEWTYPE -> FavoriteFilmViewHolder(layout.inflate(R.layout.item_favorite_film,parent, false))
            else -> FilmItemViewHolder(layout.inflate(R.layout.item_film,parent, false))
        }
    }



    override fun getItemViewType(position: Int): Int {
        return when(type){
            "main" -> FILM_ITEM_VIEWTYPE
            "favorite" -> FAVORITE_FILM_ITEM_VIEWTYPE
            else -> FILM_ITEM_VIEWTYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmItemViewHolder -> holder.bind(lst[position]  , listener)
            is FavoriteFilmViewHolder -> holder.bind(lst[position]  , listener)
        }
    }

    override fun getItemCount(): Int = lst.size

    fun getList():ArrayList<FilmItem> {
        return  lst
    }

    fun setData(itemList:ArrayList<FilmItem>) {
        lst = itemList.map{ it.copy() } as ArrayList<FilmItem>
    }

    fun updateItem(updatedLst: ArrayList<FilmItem>) {

        val diffCallback = FilmDiffUtils(getList(), updatedLst)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        setData(updatedLst)
        diffResult.dispatchUpdatesTo(this)
    }

    interface FilmItemListener{
        fun onClickItem(item:FilmItem,  position: Int)
        fun onClickCheckBoxItem(item:FilmItem,  isChecked:Boolean)
    }
}