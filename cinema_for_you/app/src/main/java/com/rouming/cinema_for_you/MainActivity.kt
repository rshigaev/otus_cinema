package com.rouming.cinema_for_you

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import com.rouming.cinema_for_you.fragment.FavoriteFilmsFragment
import com.rouming.cinema_for_you.fragment.MainFragment
import com.rouming.cinema_for_you.fragment.MainFragment.Companion.TAG
import com.rouming.cinema_for_you.roomDB.FilmRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: FilmRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        repository = FilmRepository()

//        lifecycleScope.launch{
//            for (i in 1..2){
//                try {
//                    val outFilm = retrofitService.getFilms(1,)
//                    Log.d(TAG, "получили ${outFilm}")
////                        repository.insertFilm(outFilm.makeFilm())
//                }
//                catch(err: HttpException){
//                    Log.d(TAG, "ошибка: ${err}")
//                }
//            }
//        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    private fun init(){
        Log.d(TAG, "init activity")

        val bottonNav = binding.bottomNav

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val fragment = MainFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        supportFragmentManager.setFragmentResultListener(DETAILED_RESULT, this){request_key, result ->  }
        bottonNav.setOnItemSelectedListener {
            val fragment = when (it.itemId){
                R.id.mainmenu_films -> MainFragment()
                R.id.mainmenu_favotites -> FavoriteFilmsFragment()
                else -> MainFragment()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
            true
        }
        bottonNav.setOnItemReselectedListener {
            true
        }
    }



    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }
        else CloseAppDialog().show(supportFragmentManager, "dialog")
    }

    companion object{
        const val DETAILED_RESULT= "detailed_result"
        const val TAG= "OTUS"
    }
}