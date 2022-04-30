package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class CalculateRateResponse(
    @SerializedName("result")
    val result: Boolean?,
    @SerializedName("amount")
    val amount: String? = "0.0"
)
