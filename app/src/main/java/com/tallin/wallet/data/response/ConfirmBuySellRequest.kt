package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class ConfirmBuySellRequest(
    @SerializedName("operationId")
    val operationId: Int? = null,
    @SerializedName("code")
    val code: String? = null
)