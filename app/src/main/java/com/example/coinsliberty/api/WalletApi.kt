package com.example.coinsliberty.api

import com.example.coinsliberty.data.AddressInfoResponse
import com.example.coinsliberty.data.BalanceInfoResponse
import com.example.coinsliberty.data.TransactionResponse
import com.example.coinsliberty.data.WalletInfoResponse
import retrofit2.http.GET
import retrofit2.http.POST

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