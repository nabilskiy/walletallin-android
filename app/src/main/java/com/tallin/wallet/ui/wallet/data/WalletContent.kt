package com.tallin.wallet.ui.wallet.data

data class WalletContent(
    val ico: Int,
    val title: String? = null,
    val type: String? = null,
    val price: String? = null,
    val result: String? = null,
    val itemBackground: Int,
    val color: Int,
    val coefficient:Double,
    val locked:Boolean,
    val balance:Double = .0
)