package com.rouming.cinema_for_you

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.google.android.material.snackbar.Snackbar
import com.rouming.cinema_for_you.MainActivity.Companion.TAG
import com.rouming.cinema_for_you.roomDB.AppDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FilmRemoteMediator(
    //private val query: String,
    private val database: AppDB,
    private val networkService: RetrofitService

) : RemoteMediator<Int, Film>() {
    val filmDao = database.FilmDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Film>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)

        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }


        try {


                    var response = networkService.getFilms(page = page, type ="FILM")

                    val isEndOfList = response.totalPages == page
                    if (!isEndOfList){
                        database.withTransaction {
                            filmDao.insertFilms(response.makeFilmsListFromListResponse())
                        }
                    }


            return MediatorResult.Success(endOfPaginationReached =  isEndOfList)
        } catch (exception: IOException) {
            LoadState.Error(exception)
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            LoadState.Error(exception)
            return MediatorResult.Error(exception)
        } catch (exception:Exception) {
            LoadState.Error(exception)
            return MediatorResult.Error(exception)
        }
    }

    private fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Film>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?: STARTING_PAGE_INDEX
                val result = if(remoteKeys!=null){MediatorResult.Success(endOfPaginationReached = true)}
                else STARTING_PAGE_INDEX
                result
            }
            LoadType.APPEND -> {
                val lastKey = getLastRemoteKey(state)
                return lastKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
//                val prevKey = getFirstRemoteKey(state)
//                prevKey ?: return MediatorResult.Success(
//                    endOfPaginationReached = false
//                )
//                prevKey
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }



fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Film>): Int? {
    val nextKey = if(state.anchorPosition == null) null else state.closestItemToPosition(state.anchorPosition?:0)!!.id/10
    return nextKey
}

    private fun getLastRemoteKey(state: PagingState<Int, Film>): Int? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { film ->
                film.id/20}
    }

//    private fun getFirstRemoteKey(state: PagingState<Int, Film>): Int? {
//        return state.pages
//            .firstOrNull { it.data.isNotEmpty() }
//            ?.data?.firstOrNull()
//            ?.let { film ->
//               if(film.id/10 ==0) null else film.id/10-1
//            }
//    }

    companion object{
        const val STARTING_PAGE_INDEX = 1
    }
}