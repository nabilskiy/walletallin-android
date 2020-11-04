package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("token")
    val token: String? = null
)