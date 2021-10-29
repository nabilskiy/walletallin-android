package com.tallin.wallet.data

data class RedirectsKycRequest(
    val onComplete: String,
    val onError: String
)
data class ProfileKycRequest(
    val firstName: String,
    val lastName: String
)
