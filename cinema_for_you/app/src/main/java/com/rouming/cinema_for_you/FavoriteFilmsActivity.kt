package com.rouming.cinema_for_you

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.databinding.ActivityFavoriteFilmsBinding

class FavoriteFilmsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteFilmsBinding
    private lateinit var favoriteList : ArrayList<FilmItem>
    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteFilmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setResult(RESULT_OK)


        init()
    }

    private fun init(){
        with(binding){
            favoriteList = intent.getParcelableArrayListExtra(LST)!!
            Log.d("OTUS", "получили лист")
            Log.d("OTUS", "${favoriteList}")
            recycler = favRcView
            recycler.layoutManager = LinearLayoutManager(this@FavoriteFilmsActivity)
            adapter = FilmAdapter(favoriteList, object:FilmAdapter.FilmItemListener{
                override fun onClickItem(item: FilmItem, position: Int) {}

                override fun onClickCheckBoxItem(item: FilmItem, position: Int) {}

                override fun onSwipeDelete(item: FilmItem, position: Int) {}
            }, "favorite")

            updateRVData()
        }
    }
    fun updateRVData(){
        recycler.adapter = adapter
        val filmDiffResult = DiffUtil.calculateDiff(FilmDiffUtils(adapter.getList() as MutableList<FilmItem>,favoriteList))
        filmDiffResult.dispatchUpdatesTo(adapter)
    }


    companion object{

        const val LST= "list"

    }
}