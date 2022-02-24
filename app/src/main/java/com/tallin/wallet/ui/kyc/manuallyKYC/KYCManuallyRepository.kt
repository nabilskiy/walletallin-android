package com.tallin.wallet.ui.kyc.manuallyKYC

import com.tallin.wallet.api.KycApi
import okhttp3.MultipartBody

class KYCManuallyRepository(private val api: KycApi) {
    suspend fun sendDoc(
        partMap1: MultipartBody.Part,
        partMap2: MultipartBody.Part,
       // id: Int,
       // docId: Int,
        document: MultipartBody.Part
    ) = api.sendDoc(partMap1, partMap2,/*SendDoc(id, docId),*/ document)
}