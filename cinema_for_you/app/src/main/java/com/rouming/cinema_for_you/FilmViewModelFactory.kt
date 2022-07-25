package com.rouming.cinema_for_you

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rouming.cinema_for_you.roomDB.FilmRepository

class FilmViewModelFactory () : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST") // Guaranteed to succeed at this point.
            return FilmViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}