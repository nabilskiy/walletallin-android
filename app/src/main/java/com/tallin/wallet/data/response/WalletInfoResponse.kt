package com.tallin.wallet.data.response

import com.google.gson.annotations.SerializedName

data class WalletInfoResponse(
    @SerializedName("result")
    val result: Boolean? = null,
    @SerializedName("list")
    val list: List<WalletInfoContent>? = null,
    @SerializedName("error")
    val error: ErrorResponse? = null
)

data class WalletInfoContent(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("label")
    val label: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("locked")
    val locked: Boolean? = null,
    @SerializedName("crypto")
    val crypto: Boolean? = null
)