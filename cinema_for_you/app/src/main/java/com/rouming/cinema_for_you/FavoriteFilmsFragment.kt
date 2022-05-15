package com.rouming.cinema_for_you

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteFilmsFragment : Fragment() {

    private lateinit var filmList : ArrayList<FilmItem>
    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }



    private fun init(){

        filmList = arguments?.getParcelableArrayList(LST) ?: error("no filmList")
        recycler = view?.findViewById(R.id.favorite_rcView)!!
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FilmAdapter(object:FilmAdapter.FilmItemListener{
            override fun onClickItem(item: FilmItem, position: Int) {}
            override fun onClickCheckBoxItem(view: View,item: FilmItem, isChecked: Boolean) {} },
                "favorite")

        val myTouchHelper = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT
                ) {
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false // true if moved, false otherwise
                    }
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val currentPosition = viewHolder.adapterPosition
                        filmList.filter{it.like}[currentPosition].like = false
                        adapter.updateItem(filmList.filter{it.like} as ArrayList<FilmItem>)
                    }
                })
        myTouchHelper.attachToRecyclerView(recycler)
        adapter.setData(filmList.filter{it.like} as ArrayList<FilmItem>)
        recycler.adapter = adapter
    }

    companion object{
        const val LST= "list"
    }
}