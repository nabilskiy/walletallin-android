package com.tallin.wallet.api

import com.tallin.wallet.data.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

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


    @GET("api/get-required-transaction-documents")
    suspend fun getTransactionDocumentsInfo(
        @Query ("trans_id") id: Int
    ): TransactionDocumentsInfoResponse

    @Multipart
    @POST("/api/upload")
    suspend fun sendDoc(
        @Part assign_id: MultipartBody.Part,
        @Part document_id: MultipartBody.Part,
        @Part body: MultipartBody.Part,
        @Part trans_id: MultipartBody.Part
    ): KycManuallyResponse
}