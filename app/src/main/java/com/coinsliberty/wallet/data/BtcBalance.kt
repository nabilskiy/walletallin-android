package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName

data class BtcBalance (
    @SerializedName("asset")
    val asset: String? = null,
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("otp")
    val otp: String? = null
)