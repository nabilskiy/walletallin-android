package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.response.AddressInfoResponse
import com.coinsliberty.wallet.data.response.BalanceInfoResponse
import com.coinsliberty.wallet.data.response.TransactionResponse
import com.coinsliberty.wallet.data.response.WalletInfoResponse
import retrofit2.http.GET

interface WalletApi {
    @GET("/api/list/assets")
   suspend fun walletList(): WalletInfoResponse

    @GET("/api/finance/balances")
   suspend fun getBalance(): BalanceInfoResponse

    @GET("/api/finance/transactions/btc")
   suspend fun getTransaction(): TransactionResponse

    @GET("/api/finance/address/btc")
    suspend fun getAddress(): AddressInfoResponse
}