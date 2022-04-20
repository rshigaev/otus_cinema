package com.rouming.cinema_for_you

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.rouming.cinema_for_you.databinding.ActivityMainBinding
import java.io.Serializable

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
    var filmComments = arrayListOf<String?>("","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        findViewById<Button>(R.id.btn_details_0).setOnClickListener {
            startDetailedActivity(0)
            markTouchedItem(findViewById(R.id.film0))
        }

        findViewById<Button>(R.id.btn_details_1).setOnClickListener {
            startDetailedActivity(1)
            markTouchedItem(findViewById(R.id.film1))
        }

        findViewById<Button>(R.id.btn_details_2).setOnClickListener {
            startDetailedActivity(2)
            markTouchedItem(findViewById(R.id.film2))
        }

        findViewById<Button>(R.id.btn_details_3).setOnClickListener {
            startDetailedActivity(3)
            markTouchedItem(findViewById(R.id.film3))
        }

        findViewById<Button>(R.id.btn_details_4).setOnClickListener {
            startDetailedActivity(4)
            markTouchedItem(findViewById(R.id.film4))
        }

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
                        filmLikes[requestCode] = recievedData?.getBooleanExtra(LIKE_ID, false)!!
                        filmComments[requestCode] = recievedData?.getStringExtra(COMMENT_ID)

                    }
                else -> Log.d("OTUS", "Неопознанный requestCode $requestCode")

            }
        } else {
            Log.d("OTUS", "Неуспешный результат  $resultCode")
        }

    }

    private fun init(){
        with(binding){
            tvLabel0.text = filmNames[0]
            tvLabel1.text = filmNames[1]
            tvLabel2.text = filmNames[2]
            tvLabel3.text = filmNames[3]
            tvLabel4.text = filmNames[4]

            img0.setImageResource(filmImages[0])
            img1.setImageResource(filmImages[1])
            img2.setImageResource(filmImages[2])
            img3.setImageResource(filmImages[3])
            img4.setImageResource(filmImages[4])

        }

    }

    private fun markTouchedItem(item:ConstraintLayout){
        with(binding){
            film0.setBackgroundResource(R.color.white)
            film1.setBackgroundResource(R.color.white)
            film2.setBackgroundResource(R.color.white)
            film3.setBackgroundResource(R.color.white)
            film4.setBackgroundResource(R.color.white)

            item.setBackgroundResource(R.color.light_grey)
            checkedFilm = item.id
        }
    }

    private fun startDetailedActivity(num:Int){
        val intent = Intent(this,DetailFilmInfoActivity::class.java)
        val reqCode = num
        intent.putExtra(LABEL_ID,filmNames[num])
        intent.putExtra(IMAGE_ID,filmImages[num])
        intent.putExtra(DESC_ID,filmDescriptions[num])
        intent.putExtra(LIKE_ID,filmLikes[num])
        intent.putExtra(COMMENT_ID,filmComments[num])
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



    companion object{

        const val LABEL_ID= "label"
        const val IMAGE_ID= "image"
        const val DESC_ID= "description"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"
        const val CHECKED_FILM= "checked_film"


    }
}