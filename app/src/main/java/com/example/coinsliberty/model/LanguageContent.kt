package com.example.coinsliberty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageContent(
    val languageId: Int,
    val name: Int,
    val ico: Int,
    val active: Boolean

): Parcelable