package com.rouming.cinema_for_you.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rouming.cinema_for_you.Film
import com.rouming.cinema_for_you.FilmViewModel
import com.rouming.cinema_for_you.FilmViewModelFactory
import com.rouming.cinema_for_you.R
import com.rouming.cinema_for_you.databinding.FragmentDetailedFilmInfoBinding

class DetailedFilmInfoFragment : Fragment() {

    private lateinit var filmViewModel: FilmViewModel
    private lateinit var binding: FragmentDetailedFilmInfoBinding
    private lateinit var curFilm: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.ic_back_button)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnSaveComment.setOnClickListener {
            filmViewModel.updateFilm(
                id = curFilm.id,
                comment = binding.etComments.text.toString()
            )
            parentFragmentManager.popBackStack()
        }
        binding.btnShare.setOnClickListener {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Погнали в кино на фильм ${curFilm.nameRu}!")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }


        filmViewModel = ViewModelProvider(
            this,
            FilmViewModelFactory()
        )[FilmViewModel::class.java]

        filmViewModel.detailedFilmLiveData.observe(viewLifecycleOwner) { film ->
            binding.film = film
            curFilm = film
            if(film.coverUrl != null)
            {
                Glide.with(binding.appBarImage)
                .load(film.coverUrl)
                .placeholder(R.drawable.ic_load_image)
                .error(R.drawable.ic_error_image)
                .into(binding.appBarImage)
            }
             else binding.appBarImage.setImageResource(R.drawable.ic_load_image)
        }


        binding.fabLike.setOnClickListener {
            curFilm.like = !curFilm.like
            filmViewModel.setLike(
                if(curFilm.like) "1" else "0",
                curFilm.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedFilmInfoBinding.inflate(layoutInflater)
        return binding.root
    }
}