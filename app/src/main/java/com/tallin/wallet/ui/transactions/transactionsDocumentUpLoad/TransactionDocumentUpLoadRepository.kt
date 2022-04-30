package com.tallin.wallet.ui.transactions.transactionsDocumentUpLoad

import com.tallin.wallet.api.WalletApi
import okhttp3.MultipartBody

class TransactionDocumentUpLoadRepository(private var api: WalletApi) {

    suspend fun loadFile(
        partMap1: MultipartBody.Part,
        partMap2: MultipartBody.Part,
        document: MultipartBody.Part,
        partMap3: MultipartBody.Part
    ) = api.sendDoc(partMap1, partMap2, document, partMap3)
}