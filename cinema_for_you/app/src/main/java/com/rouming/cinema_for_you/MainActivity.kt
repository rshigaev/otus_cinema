package com.rouming.cinema_for_you

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var filmNames = arrayListOf("Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок"
    )
    var filmImages = arrayListOf(R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok


        )
    var filmDescriptions = arrayListOf("Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
        "Попав в плен, Тони Старк изобретает суперкостюм и спасает мир. Блокбастер, запустивший киновселенную Marvel",
        "Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает способность превращаться в неуязвимое мультяшное существо с озорным характером.",
        "Герой Хью Джекмана воюет с собой и врагами в Японии. Светлана Ходченкова в роли обаятельной злодейки Гадюки",
        "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.",
        "Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
        "Попав в плен, Тони Старк изобретает суперкостюм и спасает мир. Блокбастер, запустивший киновселенную Marvel",
        "Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает способность превращаться в неуязвимое мультяшное существо с озорным характером.",
        "Герой Хью Джекмана воюет с собой и врагами в Японии. Светлана Ходченкова в роли обаятельной злодейки Гадюки",
        "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.",
        "Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
        "Попав в плен, Тони Старк изобретает суперкостюм и спасает мир. Блокбастер, запустивший киновселенную Marvel",
        "Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает способность превращаться в неуязвимое мультяшное существо с озорным характером.",
        "Герой Хью Джекмана воюет с собой и врагами в Японии. Светлана Ходченкова в роли обаятельной злодейки Гадюки",
        "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.",
        "Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
        "Попав в плен, Тони Старк изобретает суперкостюм и спасает мир. Блокбастер, запустивший киновселенную Marvel",
        "Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает способность превращаться в неуязвимое мультяшное существо с озорным характером.",
        "Герой Хью Джекмана воюет с собой и врагами в Японии. Светлана Ходченкова в роли обаятельной злодейки Гадюки",
        "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.")
    var filmLikes = arrayListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    var filmComments = arrayListOf<String>("","","","","","","","","","","","","","","","","","","","")

    var filmList = arrayListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            makeFilmList()
            Log.d("OTUS", "создали список занво")
        } else {
            val l:ArrayList<FilmItem> = savedInstanceState.getParcelableArrayList<FilmItem>(LST)!!
            filmList = l

            Log.d("OTUS", "восстановили список из бандл")
        }
        Log.d("OTUS", "${filmList[1]}")

        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    private fun init(){
        Log.d("OTUS", "init")

        val bottonNav = binding.bottomNav

        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        val fragment = MainFragment()
        val arguments = Bundle().apply {
            putParcelableArrayList(LST, filmList as ArrayList<out Parcelable>)
        }
        fragment.arguments = arguments
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        supportFragmentManager.setFragmentResultListener(DETAILED_RESULT, this){request_key, result ->
            if(!result.isEmpty){
                filmList[result.getInt(POSITION)].like = result.getBoolean(LIKE_ID)
                filmList[result.getInt(POSITION)].comment = result.getString(COMMENT_ID).toString()
            }
        }
        bottonNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.mainmenu_films ->
                    {
                        val fragment = MainFragment()
                        val arguments = Bundle().apply {
                            putParcelableArrayList(LST, filmList as ArrayList<out Parcelable>)
                        }
                        fragment.arguments = arguments
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit()
                    }
                R.id.mainmenu_favotites ->{
                    val fragment = FavoriteFilmsFragment()
                    val arguments = Bundle().apply {
                        putParcelableArrayList(LST, filmList as ArrayList<out Parcelable>)
                    }
                    fragment.arguments = arguments
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                else -> {
                    val fragment = MainFragment()
                    val arguments = Bundle().apply {
                        putParcelableArrayList(LST, filmList as ArrayList<out Parcelable>)
                    }
                    fragment.arguments = arguments
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
            true
        }
        bottonNav.setOnItemReselectedListener { true
        }
    }

    /*private fun startDetailedActivity(num:Int){
        val intent = Intent(this,DetailFilmInfoActivity::class.java)
        val reqCode = num
        intent.putExtra(LABEL_ID,filmList[num].label)
        intent.putExtra(IMAGE_ID,filmImages[num])
        intent.putExtra(DESC_ID,filmList[num].desc)
        intent.putExtra(LIKE_ID,filmList[num].like)
        intent.putExtra(COMMENT_ID,filmList[num].comment)
        intent.putExtra(POSITION,num)
        startActivityForResult(intent, reqCode)
    }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LST,filmList)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }
        else CloseAppDialog().show(supportFragmentManager, "dialog")
    }

    private fun makeFilmList(){
        for(i in 0 .. 18){
                filmList.add(i,
                    FilmItem(i, filmNames[i],
                    filmImages[i],
                    filmDescriptions[i],
                    filmComments[i],
                    filmLikes[i]))
            }
    }

    companion object{
        const val LST= "list"
        const val DETAILED_RESULT= "detailed_result"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"
        const val POSITION= "position"
    }
}