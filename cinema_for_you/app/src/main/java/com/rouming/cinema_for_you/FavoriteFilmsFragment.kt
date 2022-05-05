package com.rouming.cinema_for_you

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.databinding.ActivityFavoriteFilmsBinding


class FavoriteFilmsFragment : Fragment() {


    private lateinit var filmList : MutableList<FilmItem>
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

            Log.d("OTUS", "${filmList.filter{it.like}}")
            recycler = view?.findViewById(R.id.favorite_rcView)!!
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter = FilmAdapter(filmList.filter{it.like} as MutableList<FilmItem>, object:FilmAdapter.FilmItemListener{
                override fun onClickItem(item: FilmItem, position: Int) {}

                override fun onClickCheckBoxItem(item: FilmItem, position: Int) {}
            },
                "favorite")

            val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recycler)

            updateRVData()

    }

    fun updateRVData(){
        recycler.adapter = adapter
        Log.d("OTUS","получили гетлист${adapter.getList() }}")
        Log.d("OTUS","и текущий список фильмов ${filmList.filter{it.like}}")
        val filmDiffResult = DiffUtil.calculateDiff(FilmDiffUtils( filmList.filter{it.like} as MutableList<FilmItem>, adapter.getList()))
        filmDiffResult.dispatchUpdatesTo(adapter)
    }


    companion object{

        const val LST= "list"

    }

}