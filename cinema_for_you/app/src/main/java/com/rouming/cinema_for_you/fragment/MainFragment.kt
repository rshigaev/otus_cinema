package com.rouming.cinema_for_you.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rouming.cinema_for_you.*
import com.rouming.cinema_for_you.adapter.FilmPagerAdapter
import com.rouming.cinema_for_you.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment(val detailedId:Int?) : Fragment() {

    private lateinit var binding: FragmentMainBinding
    lateinit var fragment: DetailedFilmInfoFragment
    private lateinit var filmViewModel : FilmViewModel
    private lateinit var  adapter: FilmPagerAdapter
    private lateinit var  myView : View
    private var detailedIsOpened = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        myView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun openDetailedById(id:Int){

        markTouchedItem(id)
        fragment = DetailedFilmInfoFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun init() {
        binding.rcView.layoutManager = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(requireContext()) else GridLayoutManager(requireContext(),2)
             adapter = FilmPagerAdapter( object: FilmPagerAdapter.FilmItemListener {
                 override fun onClickItem(item: Film, position: Int) {
                     markTouchedItem(item.id)
                     fragment = DetailedFilmInfoFragment()
                     parentFragmentManager.beginTransaction()
                         .replace(R.id.container, fragment)
                         .addToBackStack(null)
                         .commit()
                 }
                 override fun onClickCheckBoxItem(view:View, item: Film, isChecked: Boolean){
                     var checked = isChecked
                     Log.d(TAG, "получили чект = $checked")
                     markTouchedItem(item.id)
                     val str = if(checked) R.string.add_to_favorite else R.string.remove_from_favorite
                     Snackbar.make(view,str,Snackbar.LENGTH_LONG)
                         .setAnchorView(view.findViewById(R.id.item_tvLabel))
                         .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                         .setAction(R.string.undo) { v ->
                             Snackbar
                                 .make(v, R.string.cancel_action, Snackbar.LENGTH_SHORT)
                                 .setAnchorView(view.findViewById(R.id.item_tvLabel))
                                 .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                 .show()
                             checked = !checked
                             filmViewModel.setLike(
                                 if(checked) "1" else "0",
                                 item.id)
                         }
                         .show()
                     filmViewModel.setLike(
                         if(checked) "1" else "0",
                         item.id)
                 }

                 override fun onClickReminder(view: View, item: Film) {
                    AddNotificationDialog(R.layout.date_time_picker,item).show(parentFragmentManager, "notif_dialog")
                 }
             })

        binding.rcView.adapter = adapter

        adapter.addLoadStateListener {loadState ->

            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            val loadingState = when {
                loadState.prepend is LoadState.Loading -> true
                loadState.append is LoadState.Loading -> true
                loadState.refresh is LoadState.Loading -> true
                else -> false
            }

            binding.progressDialog.isVisible = loadingState

            //Log.d(TAG, "errorState = $errorState")
            if(errorState != null){
                Snackbar.make(myView,R.string.error_download,Snackbar.LENGTH_INDEFINITE)
                    .setAnchorView(R.id.bottomNav)
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .setAction(R.string.retry) { _ ->
                        adapter.retry()
                    }
                    .show()
            }
        }

        filmViewModel = ViewModelProvider(this, FilmViewModelFactory())[FilmViewModel::class.java]

        lifecycleScope.launch {
            filmViewModel.allFilms.collectLatest {
                adapter.submitData(it) }
        }

        val openId = detailedId
        if(openId != null && !detailedIsOpened) {
            detailedIsOpened = true
            openDetailedById(openId)
        }
    }

    private fun markTouchedItem(id:Int){
        filmViewModel.markTouched(id)
    }

    companion object {
        const val TAG = "OTUS"
    }
}