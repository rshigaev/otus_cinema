package com.rouming.cinema_for_you

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailedFilmInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var label: String = ""
    private var image:Int = -1
    private var description:String? = null
    private var like:Boolean = false
    private var comment:String? = null
    private var position:Int = -1
    lateinit var tvLabel:TextView
    lateinit var imgView:ImageView
    lateinit var tvDesc:TextView
    lateinit var fabLike:FloatingActionButton
    lateinit var tvComment:TextView
    lateinit var toolbar: Toolbar
    lateinit var btnSave: Button
    lateinit var btnShare: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            label = it.getString(LABEL_ID).toString()
            image = it.getInt(IMAGE_ID)
            description = it.getString(DESC_ID)
            like = it.getBoolean(LIKE_ID)
            comment = it.getString(COMMENT_ID)
            position = it.getInt(POSITION)
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //tvLabel = view.findViewById(R.id.tv_label)
        toolbar = view.findViewById(R.id.toolbar)
        imgView = view.findViewById(R.id.app_bar_image)
        tvDesc = view.findViewById(R.id.tv_desc)
        fabLike = view.findViewById(R.id.fab_like)
        tvComment = view.findViewById(R.id.et_comments)
        btnSave = view.findViewById(R.id.btn_save_comment)
        btnShare = view.findViewById(R.id.btn_share)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //inflater.inflate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detailed_film_info, container, false)

    }

    private fun init(){
        //tvLabel.text = label
        toolbar.title = label
        imgView.setImageResource(image)
        tvDesc.text = description
        if(like) fabLike.setImageResource(R.drawable.ic_cb_like_checked) else fabLike.setImageResource(R.drawable.ic_cb_like)
        tvComment.text = comment
        fabLike.setOnClickListener {
            like = !like
            if(like) fabLike.setImageResource(R.drawable.ic_cb_like_checked) else fabLike.setImageResource(R.drawable.ic_cb_like)
        }
        btnSave.setOnClickListener {
            //TODO
        }
        btnShare.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Погнали в кино на фильм ${label}!")
            type = "text/plain"
        }
            startActivity(Intent.createChooser(shareIntent, null)) }

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