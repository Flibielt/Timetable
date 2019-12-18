package com.example.timetable.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceholderProperty(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable {

}