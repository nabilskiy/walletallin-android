package com.example.coinsliberty.api

import com.example.coinsliberty.data.response.AddressInfoResponse
import com.example.coinsliberty.data.response.BalanceInfoResponse
import com.example.coinsliberty.data.response.TransactionResponse
import com.example.coinsliberty.data.response.WalletInfoResponse
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