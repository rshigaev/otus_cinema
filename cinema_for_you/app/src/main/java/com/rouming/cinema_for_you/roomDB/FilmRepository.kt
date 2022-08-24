package com.rouming.cinema_for_you.roomDB

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.rouming.cinema_for_you.App
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.MainActivity
import com.rouming.cinema_for_you.RetrofitService
import java.util.concurrent.Executors


class FilmRepository {
    val db = AppDB.getDatabase(App.instance.applicationContext)

//    fun getAllFilms(): LiveData<PagingData<Film>> {
//        Log.d(MainActivity.TAG,"репозитори")
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                enablePlaceholders = true,
//                initialLoadSize = 1
//            ),
//            pagingSourceFactory = {
//                FilmPagingSource(retrofitService)
//            }
//            , initialKey = 1
//        ).liveData
//    }

    fun getFilms() = db.FilmDao().findAllFilms()
    fun getFavoriteFilms() = db.FilmDao().findFavoriteFilms()
    fun getSelectedFilm() = db.FilmDao().findSelectedFilm()
    fun getMaxId() = db.FilmDao().findMaxId()

    fun updateFilm(id:Int, comment:String){
        Executors.newSingleThreadScheduledExecutor().execute {
            db.FilmDao().updateFilm(id, comment)
        }
    }

    fun markTouched(int: Int){
        Executors.newSingleThreadScheduledExecutor().execute {
            db.FilmDao().markAllUntouched()
            db.FilmDao().markTouched(int)
        }
    }

    fun setLike(like:String, position: Int) {
        Executors.newSingleThreadScheduledExecutor().execute {
            db.FilmDao().setLike(like, position)
        }
    }

    fun setLikeKinopoiskId(like:String, position: Int) {
        Executors.newSingleThreadScheduledExecutor().execute {
            db.FilmDao().setLikeKinopoiskId(like, position)
        }
    }

    fun insertFilm(film: Film) {
        Executors.newSingleThreadScheduledExecutor().execute {
            db.FilmDao().insertFilm(film)
        }
    }

    fun findAllFilmsByPage():PagingSource<Int,Film>{
       // Log.d(MainActivity.TAG,"findAllFilmsByPage в FilmRepository")
        return db.FilmDao().findAllFilmsByPage()
    }

}


//https://github.com/android/architecture-components-samples/blob/githubbrowser-coroutines/PagingWithNetworkSample/app/src/main/java/com/android/example/paging/pagingwithnetwork/reddit/vo/RedditPost.kt
