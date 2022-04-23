package com.rouming.cinema_for_you

import android.graphics.drawable.Drawable

data class FilmItem(val label:String, val image: Drawable?, val desc:String, var comment:String, var like:Boolean ) {
}