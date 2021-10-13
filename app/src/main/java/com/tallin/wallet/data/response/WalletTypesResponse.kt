package com.tallin.wallet.data.response

data class WalletTypesResponse(
//    @SerializedName("result")
    val result: Boolean? = null,
//    @SerializedName("data")
    val data: ArrayList<WalletTypesData>? = null
)

data class WalletTypesData(
 //   @SerializedName("type_id")
    val type_id: Int? = null,
 //   @SerializedName("name")
    val name: String? = null,
 //   @SerializedName("default")
    val default: Boolean? = null
)
