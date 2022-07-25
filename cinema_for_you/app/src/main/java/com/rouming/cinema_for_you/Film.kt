package com.rouming.cinema_for_you

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

@Entity(tableName = "film")
    data class Film(

    @ColumnInfo(name = "description")
        val description: String? = "null",

        @ColumnInfo(name = "nameRu")
        val nameRu: String? = "null",

        @ColumnInfo(name = "nameEn")
        val nameEn: String? = "null",

        @ColumnInfo(name = "coverUrl")
        val coverUrl: String? = "null",

        @ColumnInfo(name = "slogan")
        val slogan: String? = null,

        @ColumnInfo(name = "kinopoiskId")
        val kinopoiskId: Int = -1,

        @ColumnInfo(name = "like_flg")
        var like: Boolean = false,

        @ColumnInfo(name = "is_touched")
        val isTouched: Boolean = false,

        @ColumnInfo(name = "comment")
        val comment: String = ""

) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}



data class PagedResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("totalPages") val totalPages:Int,
    @SerializedName("items") val items: List<FilmResponse>
){
    fun makeFilmsListFromListResponse():List<Film>{
        var lst = arrayListOf<Film>()
        for (item in items){
            lst.add(item.makeFilm())
        }
        return lst
    }
}

data class FilmResponse(

    @SerializedName("ratingImdb")
    val ratingImdb: Double? = null,

    @SerializedName("year")
    val year: Int? = null,

    @SerializedName("imdbId")
    val imdbId: String? = null,

    @SerializedName("filmLength")
    val filmLength: Int? = null,

    @SerializedName("description")
    val description: String = "null",

    @SerializedName("reviewsCount")
    val reviewsCount: Int? = null,

    @SerializedName("ratingGoodReview")
    val ratingGoodReview: Double? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("endYear")
    val endYear: Int? = null,

    @SerializedName("ratingRfCriticsVoteCount")
    val ratingRfCriticsVoteCount: Int? = null,

    @SerializedName("hasImax")
    val hasImax: Boolean? = null,

    @SerializedName("nameRu")
    val nameRu: String = "null",

    @SerializedName("lastSync")
    val lastSync: String? = null,

    @SerializedName("countries")
    @Expose
    val countries: List<Countries>? = null,

    @SerializedName("genres")
    @Expose
    val genres: List<Genres>? = null,

    @SerializedName("posterUrl")
    val posterUrl: String? = null,

    @SerializedName("productionStatus")
    val productionStatus: String? = null,

    @SerializedName("isTicketsAvailable")
    val isTicketsAvailable: Boolean? = null,

    @SerializedName("ratingMpaa")
    val ratingMpaa: String? = null,

    @SerializedName("ratingAgeLimits")
    val ratingAgeLimits: String? = null,

    @SerializedName("editorAnnotation")
    val editorAnnotation: String? = null,

    @SerializedName("startYear")
    val startYear: Int? = null,

    @SerializedName("ratingKinopoiskVoteCount")
    val ratingKinopoiskVoteCount: Int? = null,

    @SerializedName("nameEn")
    val nameEn: String? = "null",

    @SerializedName("shortDescription")
    val shortDescription: String = "null",

    @SerializedName("completed")
    val completed: Boolean? = null,

    @SerializedName("ratingAwaitCount")
    val ratingAwaitCount: Int? = null,

    @SerializedName("has3D")
    val has3D: Boolean? = null,

    @SerializedName("logoUrl")
    val logoUrl: String? = null,

    @SerializedName("ratingKinopoisk")
    val ratingKinopoisk: Double? = null,

    @SerializedName("coverUrl")
    val coverUrl: String = "null",

    @SerializedName("nameOriginal")
    val nameOriginal: String? = null,

    @SerializedName("ratingGoodReviewVoteCount")
    val ratingGoodReviewVoteCount: Int? = null,

    @SerializedName("serial")
    val serial: Boolean? = null,

    @SerializedName("webUrl")
    val webUrl: String? = null,

    @SerializedName("posterUrlPreview")
    val posterUrlPreview: String? = null,

    @SerializedName("shortFilm")
    val shortFilm: Boolean? = null,

    @SerializedName("ratingRfCritics")
    val ratingRfCritics: Double? = null,

    @SerializedName("ratingImdbVoteCount")
    val ratingImdbVoteCount: Int? = null,

    @SerializedName("ratingAwait")
    val ratingAwait: Double? = null,

    @SerializedName("ratingFilmCritics")
    val ratingFilmCritics: Double? = null,

    @SerializedName("slogan")
    val slogan: String? = null,

    @SerializedName("kinopoiskId")
    val kinopoiskId: Int = -1,

    @SerializedName("ratingFilmCriticsVoteCount")
    val ratingFilmCriticsVoteCount: Int? = null
) {
    fun makeFilm() = Film(if(description=="null") null else description, nameRu, nameEn,
        if(coverUrl != "null") coverUrl else if (posterUrl!= "null") posterUrl else if (posterUrlPreview!= "null") posterUrlPreview else null,
        slogan, kinopoiskId)
}



data class Genres (
    val genres: List<String>
)


data class Countries (
    val countries: List<String>
)


