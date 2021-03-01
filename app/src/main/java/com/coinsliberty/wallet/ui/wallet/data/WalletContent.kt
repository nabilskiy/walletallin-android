package com.coinsliberty.wallet.ui.wallet.data

data class WalletContent(
    val ico: Int,
    val title: String,
    val type: String? = null,
    val price: String? = null,
    val result: String? = null,
    val itemBackground: Int,
    val color: Int
)