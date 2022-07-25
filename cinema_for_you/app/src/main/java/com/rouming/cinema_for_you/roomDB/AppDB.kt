package com.rouming.cinema_for_you.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rouming.cinema_for_you.Film

object DBProps  {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "films_db"
}

@Database(entities = [Film::class], version = DBProps.DATABASE_VERSION)
abstract class AppDB:RoomDatabase() {

    abstract fun FilmDao(): FilmDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context) : AppDB {

            if (INSTANCE != null) {
                return INSTANCE as AppDB
            } else {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDB::class.java,
                            DBProps.DATABASE_NAME
                        )
                            //.fallbackToDestructiveMigration()
                            //.addMigrations()
                            .build()
                        INSTANCE = instance
                    }
                }
            }
            return INSTANCE!!
        }
    }
}