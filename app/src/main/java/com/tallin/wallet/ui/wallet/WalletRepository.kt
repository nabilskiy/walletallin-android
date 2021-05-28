package com.tallin.wallet.ui.wallet

import com.tallin.wallet.api.WalletApi

class WalletRepository(private var api: WalletApi) {
   suspend fun walletList() = api.walletList()

   suspend fun getBalance() = api.getBalance()

   suspend fun getTransactionsBtc() = api.getTransactionBtc()

   suspend fun getTransactionsEth() = api.getTransactionEth()
}