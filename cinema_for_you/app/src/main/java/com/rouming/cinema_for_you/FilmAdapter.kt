package com.rouming.cinema_for_you

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val FILM_ITEM_VIEWTYPE = 1
private const val FAVORITE_FILM_ITEM_VIEWTYPE = 2

class FilmAdapter(
    private val itemList: MutableList<FilmItem>,
    private val listener:FilmItemListener,
    private val type:String = "main")
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter  {

    val lst = itemList

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

    fun getList() = lst

    interface FilmItemListener{
        fun onClickItem(item:FilmItem,  position: Int)
        fun onClickCheckBoxItem(item:FilmItem,  position: Int)

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Log.d("OTUS","onItemMove")
    }

    override fun onItemDismiss(position: Int) {
        Log.d("OTUS","onItemDismiss")
        Log.d("OTUS","было ${lst[position].like}")
        lst[position].like = false
        Log.d("OTUS","стало ${lst[position].like}")

    }


}