package com.tallin.wallet.data.requests

import com.google.gson.annotations.SerializedName

data class SellRequest (
    @SerializedName("fiat_currency")
    val fiatCurrency: String,
    @SerializedName("fiat_amount")
    val fiatAmount: Double = 0.0
)