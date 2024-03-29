package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class SendMaxResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("amount")
    val balance: Double? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null

)