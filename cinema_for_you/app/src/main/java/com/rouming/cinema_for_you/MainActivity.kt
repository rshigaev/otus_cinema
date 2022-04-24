package com.rouming.cinema_for_you

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import java.io.Serializable
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FilmAdapter
    private lateinit var recycler: RecyclerView

    var checkedFilm : Int = -1


    var filmNames = mutableListOf("Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок",
        "Форсаж 5","Железный человек","Маска","Росомаха","Шерлок"
    )
    var filmImages = mutableListOf(R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,
        R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok


        )
    var filmDescriptions = mutableListOf("Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
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
    var filmLikes = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    var filmComments = mutableListOf<String>("","","","","","","","","","","","","","","","","","","","")

    var filmList = mutableListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            makeFilmList()
            Log.d("OTUS", "создали список занво")
        } else {
            val l:MutableList<FilmItem> = savedInstanceState.getParcelableArrayList<FilmItem>(LST) as MutableList<FilmItem>
            filmList = l

            Log.d("OTUS", "восстановили список из бандл")
        }
        Log.d("OTUS", "${filmList[1]}")

        init()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val recievedData = data
        if(resultCode == RESULT_OK){
            when(requestCode){
                777 -> {
                    val
                    filmList
                }
                else -> {
                    Log.d("OTUS", "Получили значение параметра 'Нравится' = ${recievedData?.getBooleanExtra(LIKE_ID, false)}")
                    Log.d("OTUS", "Получили значение комментария = ${recievedData?.getStringExtra(COMMENT_ID)}")
                    val position:Int = recievedData?.getIntExtra(POSITION,-1)!!
                    if(position != -1) {
                        filmList[position].like = recievedData.getBooleanExtra(LIKE_ID, false)
                        filmList[position].comment = recievedData.getStringExtra(COMMENT_ID).toString()
                    }
                    updateRVData()
                }
            }
        } else {
            Log.d("OTUS", "Неуспешный результат  $resultCode")
        }
    }

    private fun init(){
        Log.d("OTUS", "init")
        with(binding){

            recycler = rcView
            recycler.layoutManager = if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(this@MainActivity) else GridLayoutManager(this@MainActivity,2)
            adapter = FilmAdapter(filmList, object:FilmAdapter.FilmItemListener{
                override fun onClickItem(item: FilmItem, position: Int) {
                    markTouchedItem(position)
                    startDetailedActivity(position)
                    updateRVData()
                }

                override fun onClickCheckBoxItem(item: FilmItem, position: Int) {
                    val newLikeState = !item.like
                    item.like = newLikeState
                    markTouchedItem(position)
                    filmList[position].like = newLikeState
                    updateRVData()
                }

                override fun onSwipeDelete(item: FilmItem, position: Int) {}
            })
            btnFavorites.setOnClickListener {
                startFavoritesActivity()
            }
            updateRVData()
        }
    }

    private fun markTouchedItem(position:Int){
        for (i in filmList.filter { it.isTouched }){
            i.isTouched = false
        }
        filmList[position].isTouched = true
    }

    fun updateRVData(){
        recycler.adapter = adapter
        Log.d("OTUS", "проверяем изменения")
        val l = adapter.getList()
        Log.d("OTUS", "новый элемент ${l[1]}")
        Log.d("OTUS", "старый элемент ${filmList[1]}")
        val filmDiffResult = DiffUtil.calculateDiff(FilmDiffUtils(adapter.getList() as MutableList<FilmItem>,filmList))
        filmDiffResult.dispatchUpdatesTo(adapter)
    }

    private fun startFavoritesActivity(){
        val intent = Intent(this,FavoriteFilmsActivity::class.java)
        val reqCode = 777
        var lst = filmList as ArrayList<FilmItem>
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
        outState.putParcelableArrayList(LST,filmList as ArrayList<FilmItem>)
    }

    override fun onBackPressed() {
        CloseAppDialog().show(supportFragmentManager, "dialog")
    }

    private fun makeFilmList(){
        for(i in 0 .. 18){
                filmList.add(i,
                    FilmItem(filmNames[i],
                    filmImages[i],
                    filmDescriptions[i],
                    filmComments[i],
                        i,
                    filmLikes[i]))
            }
    }

    companion object{

        const val LABEL_ID= "label"
        const val IMAGE_ID= "image"
        const val DESC_ID= "description"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"
        const val CHECKED_FILM= "checked_film"
        const val POSITION= "position"
        const val LST= "list"

    }
}