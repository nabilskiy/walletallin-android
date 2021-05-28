package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("token")
    val token: String? = null
)