package com.rouming.cinema_for_you

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi


data class FilmItem(
    var id:Int, var label:String, var image: Int = -1, var desc:String = "", var comment:String = "",
    var like:Boolean = false, var isTouched:Boolean = false)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(id)
        parcel.writeString(label)
        parcel.writeInt(image)
        parcel.writeString(desc)
        parcel.writeString(comment)

        parcel.writeBoolean(like)
        parcel.writeBoolean(isTouched)
    }

    companion object CREATOR : Parcelable.Creator<FilmItem> {
        override fun createFromParcel(parcel: Parcel): FilmItem {
            return FilmItem(parcel)
        }

        override fun newArray(size: Int): Array<FilmItem?> {
            return arrayOfNulls(size)
        }
    }
}