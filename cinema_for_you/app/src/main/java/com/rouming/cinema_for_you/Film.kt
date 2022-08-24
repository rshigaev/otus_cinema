package com.rouming.cinema_for_you

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "film")
    data class Film(

    @ColumnInfo(name = "description")
    var description: String? = "null",

    @ColumnInfo(name = "nameRu")
    var nameRu: String? = "null",

    @ColumnInfo(name = "nameEn")
    var nameEn: String? = "null",

    @ColumnInfo(name = "coverUrl")
    var coverUrl: String? = "null",

    @ColumnInfo(name = "slogan")
    var slogan: String? = null,

    @ColumnInfo(name = "kinopoiskId")
    var kinopoiskId: Int = -1,

    @ColumnInfo(name = "like_flg")
    var like: Boolean = false,

    @ColumnInfo(name = "is_touched")
    var isTouched: Boolean = false,

    @ColumnInfo(name = "comment")
    var comment: String = ""

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
    var ratingImdb: Double? = null,

    @SerializedName("year")
    var year: Int? = null,

    @SerializedName("imdbId")
    var imdbId: String? = null,

    @SerializedName("filmLength")
    var filmLength: Int? = null,

    @SerializedName("description")
    var description: String = "null",

    @SerializedName("reviewsCount")
    var reviewsCount: Int? = null,

    @SerializedName("ratingGoodReview")
    var ratingGoodReview: Double? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("endYear")
    var endYear: Int? = null,

    @SerializedName("ratingRfCriticsVoteCount")
    var ratingRfCriticsVoteCount: Int? = null,

    @SerializedName("hasImax")
    var hasImax: Boolean? = null,

    @SerializedName("nameRu")
    var nameRu: String = "null",

    @SerializedName("lastSync")
    var lastSync: String? = null,

    @SerializedName("countries")
    @Expose
    var countries: List<Countries>? = null,

    @SerializedName("genres")
    @Expose
    var genres: List<Genres>? = null,

    @SerializedName("posterUrl")
    var posterUrl: String? = null,

    @SerializedName("productionStatus")
    var productionStatus: String? = null,

    @SerializedName("isTicketsAvailable")
    var isTicketsAvailable: Boolean? = null,

    @SerializedName("ratingMpaa")
    var ratingMpaa: String? = null,

    @SerializedName("ratingAgeLimits")
    var ratingAgeLimits: String? = null,

    @SerializedName("editorAnnotation")
    var editorAnnotation: String? = null,

    @SerializedName("startYear")
    var startYear: Int? = null,

    @SerializedName("ratingKinopoiskVoteCount")
    var ratingKinopoiskVoteCount: Int? = null,

    @SerializedName("nameEn")
    var nameEn: String? = "null",

    @SerializedName("shortDescription")
    var shortDescription: String = "null",

    @SerializedName("completed")
    var completed: Boolean? = null,

    @SerializedName("ratingAwaitCount")
    var ratingAwaitCount: Int? = null,

    @SerializedName("has3D")
    var has3D: Boolean? = null,

    @SerializedName("logoUrl")
    var logoUrl: String? = null,

    @SerializedName("ratingKinopoisk")
    var ratingKinopoisk: Double? = null,

    @SerializedName("coverUrl")
    var coverUrl: String = "null",

    @SerializedName("nameOriginal")
    var nameOriginal: String? = null,

    @SerializedName("ratingGoodReviewVoteCount")
    var ratingGoodReviewVoteCount: Int? = null,

    @SerializedName("serial")
    var serial: Boolean? = null,

    @SerializedName("webUrl")
    var webUrl: String? = null,

    @SerializedName("posterUrlPreview")
    var posterUrlPreview: String? = null,

    @SerializedName("shortFilm")
    var shortFilm: Boolean? = null,

    @SerializedName("ratingRfCritics")
    var ratingRfCritics: Double? = null,

    @SerializedName("ratingImdbVoteCount")
    var ratingImdbVoteCount: Int? = null,

    @SerializedName("ratingAwait")
    var ratingAwait: Double? = null,

    @SerializedName("ratingFilmCritics")
    var ratingFilmCritics: Double? = null,

    @SerializedName("slogan")
    var slogan: String? = null,

    @SerializedName("kinopoiskId")
    var kinopoiskId: Int = -1,

    @SerializedName("ratingFilmCriticsVoteCount")
    var ratingFilmCriticsVoteCount: Int? = null
) {
    fun makeFilm() = Film(if(description=="null") null else description, nameRu, nameEn,
        if(coverUrl != "null") coverUrl else if (posterUrl!= "null") posterUrl else if (posterUrlPreview!= "null") posterUrlPreview else null,
        slogan, kinopoiskId)
}



data class Genres (
    var genres: List<String>
)


data class Countries (
    var countries: List<String>
)


