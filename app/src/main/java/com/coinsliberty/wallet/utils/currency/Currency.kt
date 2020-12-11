package com.coinsliberty.wallet.utils.currency

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Currency: Parcelable {
    USD{

        override fun getTitle(): String = "USD"
    },
    EUR{

        override fun getTitle(): String = "EUR"

    };
    abstract fun getTitle(): String
}