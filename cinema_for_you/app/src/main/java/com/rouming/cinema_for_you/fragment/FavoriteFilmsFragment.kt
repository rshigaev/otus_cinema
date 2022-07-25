package com.rouming.cinema_for_you.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.FilmViewModel
import com.rouming.cinema_for_you.FilmViewModelFactory
import com.rouming.cinema_for_you.MainActivity.Companion.TAG
import com.rouming.cinema_for_you.R
import com.rouming.cinema_for_you.adapter.FilmAdapter

class FavoriteFilmsFragment : Fragment() {

    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var filmViewModel : FilmViewModel

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
        recycler = view?.findViewById(R.id.favorite_rcView)!!
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FilmAdapter()

        filmViewModel = ViewModelProvider(this, FilmViewModelFactory())[FilmViewModel::class.java]

        filmViewModel.favoriteFilmsLiveData.observe(viewLifecycleOwner) { films ->
            adapter.updateItem(films as ArrayList<Film>)
        }
        recycler.adapter = adapter

        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT)
            {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                ): Boolean {
                    return false // true if moved, false otherwise
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val currentPosition = viewHolder.bindingAdapterPosition
                    Log.d(TAG,"curpos = ${currentPosition}")
                    val curId = adapter.getFilmById(currentPosition).kinopoiskId
                    Log.d(TAG,"id = ${curId}")
                    filmViewModel.setLikeKinopoiskId(
                        "0",
                        curId)
                }
            })
            .attachToRecyclerView(recycler)


    }
}