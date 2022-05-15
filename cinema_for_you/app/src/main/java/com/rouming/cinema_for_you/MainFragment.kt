package com.rouming.cinema_for_you

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    private lateinit var recycler:RecyclerView
    lateinit var adapter:FilmAdapter
    lateinit var filmList:ArrayList<FilmItem>
    lateinit var fragment:DetailedFilmInfoFragment

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
             recycler = view?.findViewById(R.id.rcView)!!
             recycler.layoutManager = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(requireContext()) else GridLayoutManager(requireContext(),2)
             adapter = FilmAdapter( object:FilmAdapter.FilmItemListener{
                 override fun onClickItem(item: FilmItem, position: Int) {
                     markTouchedItem(position)
                     fragment = DetailedFilmInfoFragment()
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
                 override fun onClickCheckBoxItem(itemView:View, item: FilmItem, isChecked: Boolean){
                     var checked = isChecked
                     Log.d("OTUS", "получили чект = $checked")
                     markTouchedItem(item.id)
                     val str = if(checked) R.string.add_to_favorite else R.string.remove_from_favorite
                     Snackbar.make(itemView,str,Snackbar.LENGTH_LONG)
                         .setAnchorView(R.id.item_tvLabel)
                         .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                         .setAction(R.string.undo) { v ->
                             Snackbar
                                 .make(v, R.string.cancel_action, Snackbar.LENGTH_SHORT)
                                 .setAnchorView(R.id.item_tvLabel)
                                 .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                 .show()
                             checked = !checked
                            filmList[item.id].like = checked
                             adapter.updateItem(filmList)
                         }
                         .show()
                     filmList[item.id].like = checked
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

    companion object {

        const val LABEL_ID = "label"
        const val IMAGE_ID = "image"
        const val DESC_ID = "description"
        const val LIKE_ID = "like"
        const val COMMENT_ID = "comment"
        const val POSITION = "position"
        const val LST = "list"

    }
}