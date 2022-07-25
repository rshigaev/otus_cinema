package com.rouming.cinema_for_you

import android.app.Application
import android.util.Log
import com.rouming.cinema_for_you.MainActivity.Companion.TAG
import com.rouming.cinema_for_you.roomDB.AppDB
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App: Application() {

    lateinit var db: AppDB
    lateinit var api :RetrofitService

    override fun onCreate() {
        super.onCreate()
        instance = this

        db = AppDB.getDatabase(this)

        initRetrofit()
    }

    private fun initRetrofit() {
        val apiInterceptor = Interceptor {
            val originalRequest = it.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .addHeader("X-API-KEY", TOKEN)
                .addHeader("Content-Type", "application/json")
                .build()
            it.proceed(newRequest)
        }

        val logger = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(logger)
            .addNetworkInterceptor(apiInterceptor)
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES) // write timeout
            .readTimeout(2, TimeUnit.MINUTES) // read timeout
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(GET_FILM_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(RetrofitService::class.java)
    }

    companion object{

        const val GET_FILM_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
        const val TOKEN = "175acacb-1f68-4261-9b11-91a3acc2ab1a"

        lateinit var instance: App
            private set
    }
}