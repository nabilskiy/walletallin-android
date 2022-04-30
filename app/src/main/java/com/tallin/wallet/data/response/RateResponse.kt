package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RateResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("data")
    val data: List<DataRate>? = null
)

data class DataRate(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("ask_with_fee")
    val ask_with_fee: String? = null,
    @SerializedName("updated_at")
    val updatedAt: Date? = null,
    @SerializedName("timeExpiration")
    val timeExpiration: Long? = null
)
