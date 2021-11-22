package com.tallin.wallet.ui.kyc.kyc

import com.tallin.wallet.api.KycApi
import com.tallin.wallet.api.UserApi
import com.tallin.wallet.data.response.KycResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KycRepository(private val api: UserApi) {
    suspend fun getProfile() = api.getProfile()

    suspend fun getKycLink(url: String, externalId: String, partMap: HashMap<String, Any>, flowName: String, key: String): KycResponse {
        val apiKYC = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KycApi::class.java)
        return apiKYC.tokenized(externalId, partMap, flowName, key)
    }
}