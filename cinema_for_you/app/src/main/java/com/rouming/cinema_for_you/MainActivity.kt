package com.rouming.cinema_for_you

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import com.rouming.cinema_for_you.fragment.DetailedFilmInfoFragment
import com.rouming.cinema_for_you.fragment.FavoriteFilmsFragment
import com.rouming.cinema_for_you.fragment.MainFragment
import com.rouming.cinema_for_you.fragment.MainFragment.Companion.TAG
import com.rouming.cinema_for_you.roomDB.FilmRepository


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: FilmRepository
    private lateinit var filmViewModel : FilmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filmViewModel = ViewModelProvider(this, FilmViewModelFactory())[FilmViewModel::class.java]

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

        val bottomNav = binding.bottomNav

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val message = intent?.extras
        var openId = if(message != null) {
            Log.d(TAG,"экстра не пустой")
            Log.d(TAG, "${message.getString(DETAILED_ID)}")
            val openDetailedId = message.getString(DETAILED_ID)?.toInt()
            intent.removeExtra(DETAILED_ID)
            openDetailedId
        }
        else{
            Log.d(TAG,"extra пустой")
            null
        }

        val fragment = MainFragment(openId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        // supportFragmentManager.setFragmentResultListener(DETAILED_RESULT, this){request_key, result ->  }
        bottomNav.setOnItemSelectedListener {
            val fragment = when (it.itemId){
                R.id.mainmenu_films -> MainFragment(openId)
                R.id.mainmenu_favotites -> FavoriteFilmsFragment()
                else -> MainFragment(openId)
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
            true
        }
        bottomNav.setOnItemReselectedListener {
            true
        }

        //


    }



    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

//    private fun openDetailedFragment(itemId:Int){
//
//        Log.d(TAG,"зашли в оупэндетэйлдфрагмент")
//        markTouchedItem(itemId)
//        val fragment = DetailedFilmInfoFragment()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, fragment)
//            .addToBackStack(null)
//            .commit()
//    }


    private fun markTouchedItem(id:Int){
        filmViewModel.markTouched(id)
    }


    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }
        else CloseAppDialog().show(supportFragmentManager, "dialog")
    }

    companion object{
        //const val DETAILED_RESULT= "detailed_result"
        const val TAG= "OTUS"
        const val DETAILED_ID = "detailed"
        const val DETAILED_FILM_NAME = "detailed_name"
    }
}