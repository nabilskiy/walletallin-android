package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("otp")
    val otp: String? = null
)