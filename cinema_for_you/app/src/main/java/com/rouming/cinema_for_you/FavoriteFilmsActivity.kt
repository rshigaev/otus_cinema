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
    private lateinit var filmList : ArrayList<FilmItem>
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
            adapter = FilmAdapter(object:FilmAdapter.FilmItemListener{
                override fun onClickItem(item: FilmItem, position: Int) {}

                override fun onClickCheckBoxItem(item: FilmItem, isChecked: Boolean) {}
                                                                                                                        },
                "favorite")
            adapter.setData(filmList.filter{it.like} as ArrayList<FilmItem>)
            recycler.adapter = adapter

            var myTouchHelper = ItemTouchHelper(
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
                        val currentPosition = viewHolder.getAdapterPosition()
                        filmList.filter{it.like}[currentPosition].like = false
                        updateRVData()
                    }
                })
            myTouchHelper.attachToRecyclerView(recycler)

            updateRVData()
        }
    }

    override fun onBackPressed() {
        intent.putExtra(LST, filmList as ArrayList<FilmItem>)
        setResult(RESULT_OK, intent)
        finish()
    }
    fun updateRVData(){
        val filmDiffResult = DiffUtil.calculateDiff(FilmDiffUtils(adapter.getList(), filmList.filter{it.like} as MutableList<FilmItem>))
        adapter.setData(filmList.filter{it.like} as java.util.ArrayList<FilmItem>)
        filmDiffResult.dispatchUpdatesTo(adapter)
    }

    companion object{

        const val LST= "list"

    }
}