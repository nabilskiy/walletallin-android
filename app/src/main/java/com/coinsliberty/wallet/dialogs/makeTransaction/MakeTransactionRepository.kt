package com.coinsliberty.wallet.dialogs.makeTransaction

import com.coinsliberty.wallet.api.BtcApi
import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.api.WalletApi
import com.coinsliberty.wallet.data.BtcBalance

class MakeTransactionRepository(private val api: UserApi, private val apiBtc: BtcApi, private val apiAddress: WalletApi) {

    suspend fun getAddress() = apiAddress.getAddress()

    suspend fun sendBtcBalance(data: BtcBalance) = apiBtc.sendBtcRate(data)

    suspend fun getFee() = apiBtc.getFee()

//    suspend fun sendMax(data: SendMaxRequest) = apiBtc.sendMax(data)
    suspend fun sendMax(asset:String,rate :String) = apiBtc.sendMax(asset, rate)
}
