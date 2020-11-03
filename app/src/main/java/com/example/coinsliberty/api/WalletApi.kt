package com.example.coinsliberty.api

import retrofit2.http.GET

interface WalletApi {
    @GET("api/list/assets")
   suspend fun walletList(): Any
}