package com.tallin.wallet.data.requests

import com.google.gson.annotations.SerializedName

data class BuyRequest (
    @SerializedName("crypto_currency")
    val cryptoCurrency: String,
    @SerializedName("crypto_amount")
    val cryptoAmount: Double = 0.0
)