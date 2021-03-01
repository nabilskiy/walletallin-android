package com.coinsliberty.wallet.data.response

import com.google.gson.annotations.SerializedName

data class SwapLimitsResponse(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("data")
    val limitsData: SwapLimitsData
)

data class SwapLimitsData(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("min_from")
    val minFrom: Double,
    @SerializedName("min_to")
    val minTo: Double,
    @SerializedName("max_from")
    val maxFrom: Double,
    @SerializedName("max_to")
    val maxTo: Double
)

data class SwapInfoResponse(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("data")
    val info: SwapInfoData
)

data class SwapInfoData(
    @SerializedName("id")
    val id: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String,
    @SerializedName("amount_from")
    val amountFrom: Double,
    @SerializedName("amount_to")
    val amountTo: Double,
    @SerializedName("expires_at")
    val expiresAt: Long,
    @SerializedName("created_at")
    val dateCreated: String
)

data class SwapResultResponse(
    @SerializedName("result")
    val result: Boolean? = false,
    @SerializedName("error")
    val error: ErrorResponse? = null
)