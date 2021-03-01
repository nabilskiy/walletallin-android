package com.coinsliberty.wallet.ui.exchange

import com.coinsliberty.wallet.data.response.AvailableBalanceInfoContent
import java.util.*

class ExchangeUtils {
    fun getAvailableBalanceByCryproId(
        id: String,
        balance: AvailableBalanceInfoContent
    ): Double? =
        when (id.toLowerCase(Locale.ENGLISH)) {
            "btc" -> balance.btc
            "eth" -> balance.eth
            else -> null
        }

}