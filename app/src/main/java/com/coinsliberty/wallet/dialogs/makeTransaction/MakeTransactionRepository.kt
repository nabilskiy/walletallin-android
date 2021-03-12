package com.coinsliberty.wallet.dialogs.makeTransaction

import com.coinsliberty.wallet.api.BtcApi
import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.api.WalletApi
import com.coinsliberty.wallet.data.BtcBalance

class MakeTransactionRepository(
    private val api: UserApi,
    private val apiBtc: BtcApi,
    private val apiAddress: WalletApi
) {

    suspend fun getAddress(asset: String) = when (asset) {
        "eth" -> apiAddress.getAddressEth()
        else -> apiAddress.getAddressBtc()
    }

    suspend fun sendBtcBalance(data: BtcBalance) = apiBtc.sendBtcRate(data)

    suspend fun getFee(asset: String) = apiBtc.getFee(asset)

    //    suspend fun sendMax(data: SendMaxRequest) = apiBtc.sendMax(data)
    suspend fun sendMax(asset: String, rate: String) = apiBtc.sendMax(asset, rate)
}

