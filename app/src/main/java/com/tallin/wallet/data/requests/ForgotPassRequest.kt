package com.tallin.wallet.data.requests

import com.google.gson.annotations.SerializedName

data class ForgotPassRequest(
    @SerializedName("email")
    val email: String? = null
)