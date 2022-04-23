package com.rouming.cinema_for_you

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rouming.cinema_for_you.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var checkedFilm : Int = -1


    var filmNames = listOf("Форсаж 5","Железный человек","Маска","Росомаха","Шерлок")
    var filmImages = listOf(R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok)
    var filmDescriptions = listOf("Торетто и команда планируют ограбление века. Люк Хоббс дышит им в спину. Море зрелищного экшена, погонь и драк",
        "Попав в плен, Тони Старк изобретает суперкостюм и спасает мир. Блокбастер, запустивший киновселенную Marvel",
        "Скромный и застенчивый служащий банка чувствует себя неуверенно с красивыми девушками и вообще рядом с людьми. Волей судьбы к нему попадает волшебная маска, и Стенли Ипкис приобретает способность превращаться в неуязвимое мультяшное существо с озорным характером.",
        "Герой Хью Джекмана воюет с собой и врагами в Японии. Светлана Ходченкова в роли обаятельной злодейки Гадюки",
        "События разворачиваются в наши дни. Он прошел Афганистан, остался инвалидом. По возвращении в родные края встречается с загадочным, но своеобразным гениальным человеком. Тот в поиске соседа по квартире. Лондон, 2010 год. Происходят необъяснимые убийства. Скотланд-Ярд без понятия, за что хвататься. Существует лишь один человек, который в силах разрешить проблемы и найти ответы на сложные вопросы.")
    var filmLikes = arrayListOf<Boolean>(false,false,false,false,false)
    var filmComments = arrayListOf<String>("","","","","")

    var filmList = mutableListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val recievedData = data
        if(resultCode == RESULT_OK){
            when(requestCode){
                0,1,2,3,4 ->
                    {
                        Log.d("OTUS", "Получили значение параметра 'Нравится' = ${recievedData?.getBooleanExtra(LIKE_ID, false)}")
                        Log.d("OTUS", "Получили значение комментария = ${recievedData?.getStringExtra(COMMENT_ID)}")
                        val position:Int = recievedData?.getIntExtra(POSITION,-1)!!
                        if(position != -1){
                            filmList[position].like = recievedData?.getBooleanExtra(LIKE_ID, false)!!
                            filmList[position].comment = recievedData.getStringExtra(COMMENT_ID).toString()
                            binding.rcView.adapter?.notifyItemChanged(position)
                        }
                    }
                else -> Log.d("OTUS", "Неопознанный requestCode $requestCode")

            }
        } else {
            Log.d("OTUS", "Неуспешный результат  $resultCode")
        }

    }

    private fun init(){
        with(binding){
            makeFilmList()
            rcView.layoutManager = if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) LinearLayoutManager(this@MainActivity) else GridLayoutManager(this@MainActivity,2)
            rcView.adapter = FilmAdapter(filmList, object:FilmAdapter.FilmItemClickListener{
                override fun onClickItem(item: FilmItem, position: Int) {
                    startDetailedActivity(position)
                }

                override fun onClickCheckBoxItem(item: FilmItem, position: Int) {
                    val newLikeState = !item.like
                    item.like = newLikeState
                    binding.rcView.adapter?.notifyItemChanged(position)
                    filmList[position].like = newLikeState
                }
            })

        }

    }

    private fun markTouchedItem(item:ConstraintLayout){
        with(binding){

            item.setBackgroundResource(R.color.light_grey)
            checkedFilm = item.id
        }
    }

    private fun startDetailedActivity(num:Int){
        val intent = Intent(this,DetailFilmInfoActivity::class.java)
        val reqCode = num
        intent.putExtra(LABEL_ID,filmList[num].label)
        //intent.putExtra(IMAGE_ID,filmList[num].image)
        intent.putExtra(DESC_ID,filmList[num].desc)
        intent.putExtra(LIKE_ID,filmList[num].like)
        intent.putExtra(COMMENT_ID,filmList[num].comment)
        intent.putExtra(POSITION,num)
        startActivityForResult(intent, reqCode)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CHECKED_FILM,checkedFilm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val checkedFilmId = savedInstanceState.getInt(CHECKED_FILM)
        if(checkedFilmId !=-1){
            markTouchedItem(findViewById(checkedFilmId))
        }
    }

    private fun makeFilmList(){
        for (i in 0 until filmNames.size){
            filmList.add(i, FilmItem(filmNames[i],
                getDrawable(filmImages[i]),
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
        const val CHECKED_FILM= "checked_film"
        const val POSITION= "position"

    }
}