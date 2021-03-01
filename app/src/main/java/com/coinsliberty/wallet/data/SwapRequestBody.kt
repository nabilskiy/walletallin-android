package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName

data class SwapRequestBody(
    @SerializedName("rate_id")
    val rateID:String,
    @SerializedName("fee_from")
    val feeFrom:String
)
