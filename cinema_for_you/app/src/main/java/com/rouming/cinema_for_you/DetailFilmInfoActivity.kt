package com.rouming.cinema_for_you

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class DetailFilmInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film_info)

        val checkBox = findViewById<CheckBox>(R.id.cb_like)
        val saveButton = findViewById<Button>(R.id.btn_save_comment)
        val shareButton = findViewById<Button>(R.id.btn_share)
        val img = findViewById<ImageView>(R.id.img)
        val label = findViewById<TextView>(R.id.tv_label)
        val desc = findViewById<TextView>(R.id.tv_desc)


        if(intent.getIntExtra(IMAGE_ID,-1)!= -1) img.setImageResource(intent.getIntExtra(IMAGE_ID,-1))
        label.text = intent.getStringExtra(LABEL_ID)
        desc.text = intent.getStringExtra(DESC_ID)
        val liked = intent.getBooleanExtra(LIKE_ID, false)
        checkBox.isChecked = liked

        //checkBox.setOnCheckedChangeListener { button, isChecked ->  checkBox.isChecked =  }

        shareButton.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Погнали в кино на фильм ${label.text}!")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }

        saveButton.setOnClickListener {
            intent.putExtra(LIKE_ID,checkBox.isChecked)
            Log.d("OTUS" , "впихиваем в интент лайк =  ${checkBox.isChecked}")
            intent.putExtra(COMMENT_ID,findViewById<EditText>(R.id.et_comments).text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }



    companion object{

        const val LABEL_ID= "label"
        const val IMAGE_ID= "image"
        const val DESC_ID= "description"
        const val LIKE_ID= "like"
        const val COMMENT_ID= "comment"
        const val POSITION= "position"
    }
}