package com.coinsliberty.wallet.data

import com.google.gson.annotations.SerializedName
import java.util.stream.Stream

data class BtcBalance (
    @SerializedName("asset")
    val asset: String = "BTC",
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("otp")
    val otp: String? = null,
    @SerializedName("fee")
    val rate: String? = null

)