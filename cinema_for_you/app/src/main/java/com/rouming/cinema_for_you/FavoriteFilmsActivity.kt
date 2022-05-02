package com.rouming.cinema_for_you

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.databinding.ActivityFavoriteFilmsBinding


class FavoriteFilmsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteFilmsBinding
    private lateinit var filmList : MutableList<FilmItem>
    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteFilmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        with(binding){
            filmList = intent?.getParcelableArrayListExtra(LST)!!


            Log.d("OTUS", "${filmList.filter{it.like}}")
            recycler = favRcView
            recycler.layoutManager = LinearLayoutManager(this@FavoriteFilmsActivity)
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
    }

    override fun onBackPressed() {
        val lst = filmList as ArrayList<FilmItem>
        intent.putExtra(MainActivity.LST,lst)
        setResult(RESULT_OK, intent)
        super.onBackPressed()
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