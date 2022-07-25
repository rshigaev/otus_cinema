package com.rouming.cinema_for_you

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("films/")
    suspend fun getFilms(
        @Query("page") page:Int,
        @Query("type") type:String): PagedResponse
}
