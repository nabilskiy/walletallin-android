package com.tallin.wallet.ui.wallet.data

import java.util.*

data class RateContent(
    val rate: Double,
    val updatedAt: Date,
    val timeExpiration: Long
)