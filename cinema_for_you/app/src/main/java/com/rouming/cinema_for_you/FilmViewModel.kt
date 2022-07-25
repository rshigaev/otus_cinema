package com.rouming.cinema_for_you

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.rouming.cinema_for_you.MainActivity.Companion.TAG
import com.rouming.cinema_for_you.roomDB.AppDB
import com.rouming.cinema_for_you.roomDB.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmViewModel () : ViewModel() {

    private val repository = FilmRepository()
//    val filmsLiveData:LiveData<List<Film>> = getAllFilms()
    val favoriteFilmsLiveData:LiveData<List<Film>> = getFavoriteFilms()
    val detailedFilmLiveData:LiveData<Film> = getSelectedFilm()
//
//
//    val getMaxId:LiveData<Int> = repository.getMaxId()
//
//    fun getFilmList():PagingData<Film> = getAllFilms())
//    fun getAllFilms() = repository.getFilms()
    fun getFavoriteFilms() = repository.getFavoriteFilms()
    fun updateFilm(id:Int, comment:String) = repository.updateFilm(id, comment)
    fun markTouched(id:Int) = repository.markTouched(id)
//    fun insertFilm(film:Film) = repository.insertFilm(film)
    fun setLike(like:String, id: Int) = repository.setLike(like, id)
    fun setLikeKinopoiskId(like:String, kinopoiskId: Int) = repository.setLikeKinopoiskId(like, kinopoiskId)
    fun getSelectedFilm() = repository.getSelectedFilm()

    @OptIn(ExperimentalPagingApi::class)
    val allFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(

            /**
             * A good page size is a value that fills at least a few screens worth of content on a
             * large device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             *
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize = 20,

            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             *
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             */

            enablePlaceholders = true,

            /**
             * Maximum number of items a PagedList should hold in memory at once.
             *
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             */


            maxSize = 200
        ), remoteMediator = FilmRemoteMediator( App.instance.db, App.instance.api)
    ) {
        repository.findAllFilmsByPage()
    }.flow
        .cachedIn(viewModelScope)

}
