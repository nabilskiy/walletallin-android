package com.tallin.wallet.data

import com.google.gson.annotations.SerializedName

data class BtcBalance (
    @SerializedName("asset")
    val asset: String = "BTC",
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("fee")
    val rate: String? = null,
    @SerializedName("replaceable")
    val replaceable: Boolean = false

)