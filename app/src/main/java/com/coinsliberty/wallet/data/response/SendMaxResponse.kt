package com.coinsliberty.wallet.data.response

import com.google.gson.annotations.SerializedName

data class SendMaxResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("withdrawData")
    val withdrawData: WithdrawData? = null
)

data class WithdrawData(
    @SerializedName("available")
    val available: Double? = null,
    @SerializedName("fee")
    val fee: Double? = null,
    @SerializedName("rate")
    val rate: Double? = null,
    @SerializedName("max")
    val max: Double? = null
)