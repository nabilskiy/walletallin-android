package com.example.coinsliberty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageContent(
    val name: Int,
    val ico: Int,
    var active: Boolean

): Parcelable