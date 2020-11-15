package com.coinsliberty.wallet.dialogs.sendDialog

import com.coinsliberty.wallet.api.BtcApi
import com.coinsliberty.wallet.data.BtcBalance

class BtcRepository(private val api: BtcApi) {
    suspend fun sendBtcBalance(data: BtcBalance) = api.sendBtcRate(data)
}