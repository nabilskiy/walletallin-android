package com.tallin.wallet.utils.currency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Currency : Parcelable {
    USD {

        override fun getTitle(): String = "USD"

        override fun getSymbol(): String = "$"
    },
    EUR {

        override fun getTitle(): String = "EUR"

        override fun getSymbol(): String = "€"

    };

    abstract fun getTitle(): String
    abstract fun getSymbol(): String
}