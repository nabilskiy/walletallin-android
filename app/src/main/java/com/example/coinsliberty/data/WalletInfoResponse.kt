package com.example.coinsliberty.data

import com.google.gson.annotations.SerializedName

data class WalletInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("list")
    val list: List<WalletInfoContent>? = null
)

data class WalletInfoContent(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("locked")
    val locked: Boolean? = null
)