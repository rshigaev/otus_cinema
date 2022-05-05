package com.rouming.cinema_for_you

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainFragment : Fragment() {

    lateinit var recycler:RecyclerView
    lateinit var adapter:FilmAdapter
    lateinit var filmList:MutableList<FilmItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    private fun init(){
        Log.d("OTUS", "init")
             filmList = arguments?.getParcelableArrayList(LST) ?: error("no filmList")
             recycler = view?.findViewById<RecyclerView>(R.id.rcView)!!
             recycler.layoutManager = if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(requireContext()) else GridLayoutManager(requireContext(),2)
             adapter = FilmAdapter(filmList,  object:FilmAdapter.FilmItemListener{
                 override fun onClickItem(item: FilmItem, position: Int) {
                     markTouchedItem(position)
                     //startDetailedActivity(position)
                     updateRVData()
                 }

                 override fun onClickCheckBoxItem(item: FilmItem, position: Int) {
                     val newLikeState = !item.like
                     item.like = newLikeState
                     markTouchedItem(position)
                     filmList[position].like = newLikeState
                     updateRVData()
                 }


             })

             recycler.adapter = adapter
             updateRVData()
         }
    private fun markTouchedItem(position:Int){
        for (i in filmList.filter { it.isTouched }){
            i.isTouched = false
        }
        filmList[position].isTouched = true
    }


    fun updateRVData(){
        Log.d("OTUS", "проверяем изменения")
        val l = adapter.getList()
        Log.d("OTUS", "новый элемент ${l[1]}")
        Log.d("OTUS", "старый элемент ${filmList[1]}")
        val filmDiffResult = DiffUtil.calculateDiff(FilmDiffUtils(filmList, adapter.getList()))
        filmDiffResult.dispatchUpdatesTo(adapter)
        recycler.adapter = adapter
    }


companion object{

    const val LABEL_ID= "label"
    const val IMAGE_ID= "image"
    const val DESC_ID= "description"
    const val LIKE_ID= "like"
    const val COMMENT_ID= "comment"
    const val POSITION= "position"
    const val LST= "list"

}

}