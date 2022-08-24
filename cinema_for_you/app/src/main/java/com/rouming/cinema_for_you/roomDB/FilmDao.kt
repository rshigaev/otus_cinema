package com.rouming.cinema_for_you.roomDB

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.rouming.cinema_for_you.Film

@Dao
interface FilmDao {

    @Query("SELECT * FROM film WHERE id LIKE :gId")
    fun findFilmById(gId: String): LiveData<Film>

    @Query("SELECT * FROM film WHERE is_touched = '1'")
    fun findSelectedFilm(): LiveData<Film>

    @Query("SELECT * FROM film")
    fun findAllFilms(): LiveData<List<Film>>

    @Query("SELECT max(id) FROM film")
    fun findMaxId(): LiveData<Int>

    @Query("SELECT * FROM film where like_flg ='1'")
    fun findFavoriteFilms(): LiveData<List<Film>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilms(films:List<Film>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilm(film:Film)

    @Delete
    fun deleteFilm(film: Film)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFilms(vararg Films:Film?)

    @Query("UPDATE film set comment = :comment where id = :id")
    fun updateFilm(id:Int, comment:String)

    @Query("UPDATE film set is_touched = '0'")
    fun markAllUntouched()

    @Query("UPDATE film set is_touched = '1' where id = :id")
    fun markTouched(id: Int)

    @Query("UPDATE film set like_flg = :like where id = :id")
    fun setLike(like: String, id: Int)

    @Query("UPDATE film set like_flg = :like where kinopoiskId = :id")
    fun setLikeKinopoiskId(like: String, id: Int)

    @Query("SELECT * FROM film order by id asc")
    fun findAllFilmsByPage(): PagingSource<Int, Film>

}