package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class BalanceInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("balances")
    val balances: BalanceInfoContent? = null,
    @SerializedName("available_balances")
    val availableBalances: AvailableBalanceInfoContent? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("rates")
    val rates: RatesPrice? = null
)

data class BalanceInfoContent(
    @SerializedName("btc")
    val btc: Double? = null,
    @SerializedName("eth")
    val eth: Double? = null,
    @SerializedName("usdt")
    val usdt: Double? = null
)

data class AvailableBalanceInfoContent(
    @SerializedName("btc")
    val btc: Double? = null,
    @SerializedName("eth")
    val eth: Double? = null,
    @SerializedName("usdt")
    val usdt: Double? = null
) {
    fun getByCode(code: String?): Double =
        when (code) {
            "btc" -> btc ?: 0.0
            "eth" -> eth ?: 0.0
            "usdt" -> usdt ?: 0.0
            else -> .0
        }

}

data class RatesPrice(
    @SerializedName("btc")
    val btc: Currency? = null,
    @SerializedName("eth")
    val eth: Currency? = null
)

data class Currency(
    @SerializedName("usd")
    val usd: Double? = null,
    @SerializedName("eur")
    val eur: Double? = null

)

