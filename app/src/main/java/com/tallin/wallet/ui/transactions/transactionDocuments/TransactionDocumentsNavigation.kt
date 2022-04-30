package com.tallin.wallet.ui.transactions.transactionDocuments

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseNavigator
import com.tallin.wallet.data.response.DataTransactionDocumentsInfoResponse

class TransactionDocumentsNavigation: BaseNavigator() {

    fun goToTransactionDocumentFragment(
        navController: NavController?,
        docData: DataTransactionDocumentsInfoResponse,
        id: Int
    ){
        val bundle = bundleOf(
            "[transactionDocs]assignId" to docData.assignId,
            "[transactionDocs]name" to docData.kycDocument?.name,
            "[transactionDocs]docId" to docData.documentId,
            "[transactionDocs]transId" to id,
        )
        navController?.navigate(R.id.TransactionDocumentUpLoadFragment, bundle)
    }
}