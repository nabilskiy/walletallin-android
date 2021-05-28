package com.tallin.wallet.dialogs.sendDialog

import com.tallin.wallet.api.BtcApi
import com.tallin.wallet.data.BtcBalance

class BtcRepository(private val api: BtcApi) {
    suspend fun sendBtcBalance(data: BtcBalance) = api.sendBtcRate(data)
}