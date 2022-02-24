package com.tallin.wallet.ui.actions.buy

import com.tallin.wallet.api.ActionsApi
import com.tallin.wallet.api.WalletApi
import com.tallin.wallet.data.requests.RateRequest

class BuySellRepository(private val api: WalletApi, private val api2: ActionsApi) {

    suspend fun walletList() = api.walletList()

    suspend fun getBalance() = api.getBalance()

    //suspend fun getTransactionsBtc() = api.getTransactionBtc()

    suspend fun getRate(rateReq: RateRequest) = api2.getRate(rateReq)

    //suspend fun getTransactionsEth() = api.getTransactionEth()


}