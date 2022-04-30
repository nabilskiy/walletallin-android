package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class GetCurrenciesResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("data")
    val data: Data? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null
)

data class Data(
    @SerializedName("crypto")
    val crypto: List<String> = listOf(),
    @SerializedName("fiat")
    val fiat: List<String> = listOf()
)
