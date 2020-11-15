package com.coinsliberty.wallet.ui.wallet

import com.coinsliberty.wallet.api.WalletApi

class WalletRepository(private var api: WalletApi) {
   suspend fun walletList() = api.walletList()

   suspend fun getBalance() = api.getBalance()

   suspend fun getTransactions() = api.getTransaction()
}