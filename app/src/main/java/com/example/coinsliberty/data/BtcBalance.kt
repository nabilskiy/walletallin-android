package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class BtcBalance (
    @SerializedName("asset")
    val asset: String? = null,
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("address")
    val address: String? = null
)