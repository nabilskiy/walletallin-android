package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("rate")
    val rate: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: Long? = null
)
