package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class BuySellResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("operationId")
    val operationId: Int? = null,
    @SerializedName("message")
    val message: String? = null
)