package com.tallin.wallet.data.response

data class KycResponse(
    val url: String?,
    val responseCode: String?
)

data class KycManuallyResponse(
    val result: Boolean?,
    //val error:
    val files: Unit?
)