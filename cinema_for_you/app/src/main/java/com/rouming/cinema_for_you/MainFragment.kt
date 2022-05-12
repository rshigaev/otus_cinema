package com.rouming.cinema_for_you

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
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
    lateinit var filmList:ArrayList<FilmItem>

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
             recycler.layoutManager = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(requireContext()) else GridLayoutManager(requireContext(),2)
             adapter = FilmAdapter( object:FilmAdapter.FilmItemListener{
                 override fun onClickItem(item: FilmItem, position: Int) {
                     markTouchedItem(position)
                     val fragment = DetailedFilmInfoFragment()
                     val arguments = Bundle().apply {
                         putString(LABEL_ID, item.label)
                         putInt(IMAGE_ID, item.image)
                         putString(DESC_ID, item.desc)
                         putBoolean(LIKE_ID, item.like)
                         putString(COMMENT_ID, item.comment)
                         putInt(POSITION, position)
                     }
                     fragment.arguments = arguments
                     adapter.updateItem(filmList)
                     parentFragmentManager.beginTransaction()
                         .replace(R.id.container, fragment)
                         .addToBackStack(null)
                         .commit()
                 }
                 override fun onClickCheckBoxItem(item: FilmItem, isChecked: Boolean){
                     markTouchedItem(item.id)
                     filmList[item.id].like = isChecked
                     adapter.updateItem(filmList)
                 }
             })
             adapter.setData(filmList)
             recycler.adapter = adapter
         }
    private fun markTouchedItem(position:Int){
        for (i in filmList.filter { it.isTouched }){
            i.isTouched = false
        }
        filmList[position].isTouched = true
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