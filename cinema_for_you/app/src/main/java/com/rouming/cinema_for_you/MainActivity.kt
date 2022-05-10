package com.rouming.cinema_for_you

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView


    private var filmNames = arrayListOf("Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок"
    )
    private var filmImages = arrayListOf(R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok


        )
    private var filmDescriptions = arrayListOf("Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
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
    private var filmLikes = arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    private var filmComments = arrayListOf("","","","","","","","","","","","","","","","","","","","")

    var filmList = arrayListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            makeFilmList()

        } else {
            val l: ArrayList<FilmItem> = savedInstanceState.getParcelableArrayList(LST)!!
            filmList = l
        }
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val recievedData = data

        if(resultCode == RESULT_OK){
            when(requestCode){
                777 -> {
                    adapter.setData(filmList)
                    if(data != null){
                        val newFilmList = data.getParcelableArrayListExtra<FilmItem>(LST)

                        filmList = newFilmList!!
                        init()
                    }
                }
                else -> {
                    val position:Int = recievedData?.getIntExtra(POSITION,-1)!!
                    if(position != -1) {
                        filmList[position].like = recievedData.getBooleanExtra(LIKE_ID, false)
                        filmList[position].comment = recievedData.getStringExtra(COMMENT_ID).toString()
                        adapter.updateItem(filmList)
                    }
                }
            }
        } else {
            Log.d("OTUS", "Неуспешный результат  $resultCode")
        }

    }

    private fun init(){
        with(binding){
            recycler = rcView
            recycler.layoutManager = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(this@MainActivity) else GridLayoutManager(this@MainActivity,2)
            adapter = FilmAdapter( object:FilmAdapter.FilmItemListener{
                override fun onClickItem(item: FilmItem, position: Int) {
                    markTouchedItem(position)
                    startDetailedActivity(position)
                    adapter.updateItem(filmList)
                }

                override fun onClickCheckBoxItem(item: FilmItem,  isChecked:Boolean) {
                    markTouchedItem(item.id)
                    filmList[item.id].like = isChecked
                    adapter.updateItem(filmList)
                }
            })
            adapter.setData(filmList)
            btnFavorites.setOnClickListener {
                startFavoritesActivity()
            }
            recycler.adapter = adapter
        }
    }

    private fun markTouchedItem(position:Int){
        for (i in filmList.filter { it.isTouched }){
            i.isTouched = false
        }
        filmList[position].isTouched = true
    }

    private fun startFavoritesActivity(){
        val intent = Intent(this,FavoriteFilmsActivity::class.java)
        val reqCode = 777
        val lst = filmList
        intent.putExtra(LST,lst)
        startActivityForResult(intent, reqCode)
    }

    private fun startDetailedActivity(num:Int){
        val intent = Intent(this,DetailFilmInfoActivity::class.java)
        val reqCode = num
        intent.putExtra(LABEL_ID,filmList[num].label)
        intent.putExtra(IMAGE_ID,filmImages[num])
        intent.putExtra(DESC_ID,filmList[num].desc)
        intent.putExtra(LIKE_ID,filmList[num].like)
        intent.putExtra(COMMENT_ID,filmList[num].comment)
        intent.putExtra(POSITION,num)
        startActivityForResult(intent, reqCode)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LST,filmList)
    }

    override fun onBackPressed() {
        CloseAppDialog().show(supportFragmentManager, "dialog")
    }

    private fun makeFilmList(){
        for(i in 0 .. 18){
                filmList.add(i,
                    FilmItem(i,
                        filmNames[i],
                    filmImages[i],
                    filmDescriptions[i],
                    filmComments[i],
                    filmLikes[i]))
            }
    }

    companion object{

        const val LABEL_ID= "label"
        const val IMAGE_ID= "image"
        const val DESC_ID= "description"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"
        const val POSITION= "position"
        const val LST= "list"

    }
}