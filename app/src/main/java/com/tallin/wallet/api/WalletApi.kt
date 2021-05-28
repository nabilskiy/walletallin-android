package com.tallin.wallet.api

import com.tallin.wallet.data.response.AddressInfoResponse
import com.tallin.wallet.data.response.BalanceInfoResponse
import com.tallin.wallet.data.response.TransactionResponse
import com.tallin.wallet.data.response.WalletInfoResponse
import retrofit2.http.GET

interface WalletApi {
    @GET("/api/list/assets")
    suspend fun walletList(): WalletInfoResponse

    @GET("/api/finance/balances")
    suspend fun getBalance(): BalanceInfoResponse

    @GET("/api/finance/transactions/btc")
    suspend fun getTransactionBtc(): TransactionResponse


    @GET("/api/finance/transactions/eth")
    suspend fun getTransactionEth(): TransactionResponse

    @GET("/api/finance/address/btc")
    suspend fun getAddressBtc(): AddressInfoResponse

    @GET("/api/finance/address/eth")
    suspend fun getAddressEth(): AddressInfoResponse


}