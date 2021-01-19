package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("token")
    val password: String? = null
)