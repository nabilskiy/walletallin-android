package com.example.coinsliberty.ui.wallet

import com.example.coinsliberty.api.WalletApi

class WalletRepository(private var api: WalletApi) {
   suspend fun walletList() = api.walletList()

   suspend fun getBalance() = api.getBalance()

   suspend fun getTransactions() = api.getTransaction()
}