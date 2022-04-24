package com.rouming.cinema_for_you

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi


data class FilmItem(val label:String, val image: Int, val desc:String, var comment:String, var index:Int, var like:Boolean = false, var isTouched:Boolean = false )
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {  }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(label)
        parcel.writeInt(image)
        parcel.writeString(desc)
        parcel.writeString(comment)
        parcel.writeInt(index)
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