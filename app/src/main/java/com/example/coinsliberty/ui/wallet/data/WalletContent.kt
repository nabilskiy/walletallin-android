package com.example.coinsliberty.ui.wallet.data

data class WalletContent(
    val ico: Int,
    val title: Int,
    val type: Int,
    var price: Int? = null,
    val result: Int? = null,
    val itemBackground: Int
)