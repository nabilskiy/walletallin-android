package com.tallin.wallet.data.requests

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BuySellRequest (
    @SerializedName("fiatCurrency")
    val fiatCurrency: String,
    @SerializedName("cryptoCurrency")
    val cryptoCurrency: String,
    @SerializedName("amountFrom")
    val amountFrom: String,
    @SerializedName("amountTo")
    val amountTo: String
)