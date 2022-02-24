package com.tallin.wallet.data.requests

import com.google.gson.annotations.SerializedName

data class RateRequest(
    @SerializedName("fiat_currency")
    val fiatCurrency: String,
    @SerializedName("crypto_currency")
    val cryptoCurrency: String,
    @SerializedName("crypto_amount")
    val cryptoAmount: Int = 0,
    @SerializedName("fiat_amount")
    val fiatAmount: Int = 0
)
