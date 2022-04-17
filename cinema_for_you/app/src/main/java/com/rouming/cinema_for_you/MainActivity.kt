package com.rouming.cinema_for_you

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.rouming.cinema_for_you.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var filmNames = listOf("Форсаж 5","Железеый человек","Маска","Росомаха","Шерлок Холмс")
    var filmImages = listOf(R.drawable.ic_fast5,R.drawable.ic_ironman,R.drawable.ic_maska,R.drawable.ic_wolverine,R.drawable.ic_scherlok)
    var filmDescriptions = listOf("Форсаж 5","Железеый человек","Маска","Росомаха","Шерлок Холмс")
    var filmLikes = listOf(false,false,false,false,false)
    var filmComments = listOf("","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        init()

        findViewById<Button>(R.id.btn_details_0).setOnClickListener {
            val intent = Intent(this,DetailFilmInfoActivity::class.java)
            val reqCode = 0
            intent.putExtra(LABEL_ID,filmNames[0])
            intent.putExtra(IMAGE_ID,filmImages[0])
            intent.putExtra(DESC_ID,filmDescriptions[0])
            intent.putExtra(LIKE_ID,filmLikes[0])
            intent.putExtra(COMMENT_ID,filmComments[0])
            startActivityForResult(intent, reqCode)
        }

        findViewById<Button>(R.id.btn_details_1).setOnClickListener {
            val intent = Intent(this,DetailFilmInfoActivity::class.java)
            val reqCode = 1
            intent.putExtra(LABEL_ID,filmNames[1])
            intent.putExtra(IMAGE_ID,filmImages[1])
            intent.putExtra(DESC_ID,filmDescriptions[1])
            intent.putExtra(LIKE_ID,filmLikes[1])
            intent.putExtra(COMMENT_ID,filmComments[1])
            startActivityForResult(intent, reqCode)
        }

        /*findViewById<Button>(R.id.btn_details_2).setOnClickListener {
            val intent = Intent(this,DetailFilmInfoActivity::class.java)
            val reqCode = 2
            intent.putExtra(LABEL_ID,filmNames[2])
            intent.putExtra(IMAGE_ID,filmImages[2])
            intent.putExtra(DESC_ID,filmDescriptions[2])
            intent.putExtra(LIKE_ID,filmLikes[2])
            intent.putExtra(COMMENT_ID,filmComments[2])
            startActivityForResult(intent, reqCode)
        }

        findViewById<Button>(R.id.btn_details_3).setOnClickListener {
            val intent = Intent(this,DetailFilmInfoActivity::class.java)
            val reqCode = 3
            intent.putExtra(LABEL_ID,filmNames[3])
            intent.putExtra(IMAGE_ID,filmImages[3])
            intent.putExtra(DESC_ID,filmDescriptions[3])
            intent.putExtra(LIKE_ID,filmLikes[3])
            intent.putExtra(COMMENT_ID,filmComments[3])
            startActivityForResult(intent, reqCode)
        }

        findViewById<Button>(R.id.btn_details_4).setOnClickListener {
            val intent = Intent(this,DetailFilmInfoActivity::class.java)
            val reqCode = 4
            intent.putExtra(LABEL_ID,filmNames[4])
            intent.putExtra(IMAGE_ID,filmImages[4])
            intent.putExtra(DESC_ID,filmDescriptions[4])
            intent.putExtra(LIKE_ID,filmLikes[4])
            intent.putExtra(COMMENT_ID,filmComments[4])
            startActivityForResult(intent, reqCode)
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val recievedData = data
        if(resultCode == RESULT_OK){
            when(requestCode){
                0,1,2,3,4 ->
                    {
                        Log.d("custom_log", "Получили значение параметра 'Нравится' = ${recievedData?.getBooleanExtra(LIKE_ID, false)}")
                        Log.d("custom_log", "Получили значение комментария = ${recievedData?.getStringExtra(COMMENT_ID)}")
                    }
                else -> Log.d("custom_log", "Неопознанный requestCode $requestCode")



            }
        } else {
            Log.d("custom_log", "Неуспешный результат  $resultCode")
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



    companion object{

        const val LABEL_ID= "label"
        const val IMAGE_ID= "image"
        const val DESC_ID= "description"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"

    }
}