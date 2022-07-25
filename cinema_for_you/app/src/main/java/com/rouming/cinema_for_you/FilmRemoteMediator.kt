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
        Log.d(TAG, "load")
        val pageKeyData = getKeyPageData(loadType, state)

        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        Log.d(TAG, "page = $page")

        try {
            Log.d(TAG, "пускаем ретрофит")

                    var response = networkService.getFilms(page = page, type ="FILM")
                    Log.d(TAG, "получили респонс = $response")
                    val isEndOfList = response.totalPages == page
                    if (!isEndOfList){
                        database.withTransaction {
                            Log.d(TAG, "записываем в бд респонс")
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
                Log.d(TAG, "REFRESH")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d(TAG, "remoteKeys = $remoteKeys")
//                remoteKeys?: STARTING_PAGE_INDEX
                val result = if(remoteKeys!=null){MediatorResult.Success(endOfPaginationReached = true)}
                else STARTING_PAGE_INDEX
                Log.d(TAG, "resurl = $result")
                result
            }
            LoadType.APPEND -> {
                Log.d(TAG, "APPEND")
                val lastKey = getLastRemoteKey(state)
                return lastKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                Log.d(TAG, "PREPEND")
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
    Log.d(TAG, "state.anchorPosition = ${state.anchorPosition}")
     val nextKey = if(state.anchorPosition == null) null else state.closestItemToPosition(state.anchorPosition?:0)!!.id/10
    Log.d(TAG, "проверяем че возвращаем когда нет данных в бд: ${if(state.anchorPosition == null) null else state.closestItemToPosition(state.anchorPosition?:0)!!.id/10}")
    return nextKey
}

    private fun getLastRemoteKey(state: PagingState<Int, Film>): Int? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { film ->
                Log.d(TAG, "getLastRemoteKey, последняя загруженная страница = ${film.id/20}")
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