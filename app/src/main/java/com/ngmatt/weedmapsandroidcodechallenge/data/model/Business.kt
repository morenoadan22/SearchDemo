package com.ngmatt.weedmapsandroidcodechallenge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Business(
    val id: String,
    var rating: Float?,
    var price: String?,
    var name: String?,
    var url: String?,
    var image_url: String?,
    var distance: Float?,
    var review: String? = null
) : Parcelable
