package com.rouming.cinema_for_you

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var filmNames = listOf("Форсаж 5","Железеый человек","Маска","Росомаха","Шерлок Холмс")
        var filmImages = listOf("Форсаж 5","Железеый человек","Маска","Росомаха","Шерлок Холмс")
        var filmDescriptions = listOf("Форсаж 5","Железеый человек","Маска","Росомаха","Шерлок Холмс")


    }
}