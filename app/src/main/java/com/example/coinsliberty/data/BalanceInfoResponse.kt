package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class BalanceInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("balances")
    val balances: BalanceInfoContent? = null
)

data class BalanceInfoContent(
    @SerializedName("btc")
    val btc: Double? = null,
    @SerializedName("eth")
    val eth: Double? = null,
    @SerializedName("usdt")
    val usdt: Double? = null
)