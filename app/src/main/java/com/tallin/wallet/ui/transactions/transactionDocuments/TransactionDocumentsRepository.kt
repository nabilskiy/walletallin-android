package com.tallin.wallet.ui.transactions.transactionDocuments

import com.tallin.wallet.api.WalletApi

class TransactionDocumentsRepository(private val api: WalletApi) {
    suspend fun getTransactionDocumentsInfo(id: Int) = api.getTransactionDocumentsInfo(id)
}