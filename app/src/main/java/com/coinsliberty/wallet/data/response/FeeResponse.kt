package com.coinsliberty.wallet.data.response

import com.google.gson.annotations.SerializedName

data class FeeResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("rates")
    val rates: Rates? = null
)

data class Rates(
    @SerializedName("min")
    val min: Long? = null,
    @SerializedName("mid")
    val mid: Long? = null,
    @SerializedName("max")
    val max: Long? = null
)