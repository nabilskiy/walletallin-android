package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class FeeResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("rates")
    val rates: Rates? = null
)

data class Rates(
    @SerializedName("min")
    var min: Double? = 0.0,
    @SerializedName("mid")
    var mid: Double? = 0.0,
    @SerializedName("max")
    var max: Double? = 0.0
)