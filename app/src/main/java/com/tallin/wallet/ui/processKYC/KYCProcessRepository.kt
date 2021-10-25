package com.tallin.wallet.ui.processKYC

import com.tallin.wallet.BuildConfig
import com.tallin.wallet.api.KycApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KYCProcessRepository(/*private val api: KycApi*/) {
    private val apiKYC = Retrofit.Builder()
        .baseUrl(BuildConfig.API_KYC_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KycApi::class.java)

    suspend fun getKycLink(externalId: String, partMap: HashMap<String, Any>) = apiKYC.tokenized(externalId, partMap)
}