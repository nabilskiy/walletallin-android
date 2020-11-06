package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class AddressInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("address")
    val address: String? = null

//    @SerializedName("addressInfo")
//    val addressInfo: AddressInfoContent? = null
)

//data class AddressInfoContent(
//    @SerializedName("btc")
//    val btc: Double? = null,
//    @SerializedName("eth")
//    val eth: Double? = null,
//    @SerializedName("usdt")
//    val usdt: Double? = null,
//    @SerializedName("address")
//    val address: String? = null
//)

