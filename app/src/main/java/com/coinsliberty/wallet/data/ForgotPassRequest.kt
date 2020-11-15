package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName

data class ForgotPassRequest(
    @SerializedName("email")
    val email: String? = null
)