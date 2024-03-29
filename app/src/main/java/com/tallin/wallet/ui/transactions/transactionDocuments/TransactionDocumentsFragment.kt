package com.tallin.wallet.ui.transactions.transactionDocuments

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseAdapter
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.TransactionDocumentsInfoResponse
import com.tallin.wallet.ui.transactions.transactionDocuments.adapters.TransactionDocumentsHolder
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_transaction_documents.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionDocumentsFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_transaction_documents
    override val viewModel: TransactionDocumentsViewModel by viewModel()
    override val navigator: TransactionDocumentsNavigation = get()

    private var transId: Int? = null
    private val adapter = BaseAdapter()
        .map(R.layout.item_transaction_documents, TransactionDocumentsHolder{
            if (transId != null) navigator.goToTransactionDocumentFragment(navController, it, transId!!)
        })

    override fun onStart() {
        super.onStart()
        rvDocs.adapter = null
        adapter.removeAll()
        rvDocs.adapter = adapter
        transId = arguments?.getInt("[transactionDocs]id")
        if (transId != null){
            viewModel.getTransactionDocsInfo(transId!!)
        } else activity?.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.transactionDocsInfoLiveData, ::showResult)
    }

    private fun showResult(result: TransactionDocumentsInfoResponse){
        if (result.result == true){
            adapter.itemsLoaded(result.data)
        } else activity?.onBackPressed()
    }
}