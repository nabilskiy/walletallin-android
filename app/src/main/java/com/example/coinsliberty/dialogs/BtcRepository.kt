package com.example.coinsliberty.dialogs

import com.example.coinsliberty.api.BtcApi
import com.example.coinsliberty.data.BtcBalance

class BtcRepository(private val api: BtcApi) {
    suspend fun sendBtcBalance(data: BtcBalance) = api.sendBtcRate(data)
}