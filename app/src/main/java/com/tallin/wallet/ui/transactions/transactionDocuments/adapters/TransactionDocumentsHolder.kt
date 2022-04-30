package com.tallin.wallet.ui.transactions.transactionDocuments.adapters

import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.Holder
import com.tallin.wallet.data.response.DataTransactionDocumentsInfoResponse
import com.tallin.wallet.utils.extensions.invisible
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.item_transaction_documents.view.*

class TransactionDocumentsHolder(
        private val onItemClick: (DataTransactionDocumentsInfoResponse) -> Unit
) : Holder<DataTransactionDocumentsInfoResponse>() {
        override fun bind(itemView: View, item: DataTransactionDocumentsInfoResponse) {
                itemView.tvTitle.text = item.kycDocument?.name ?: ""
                when(item.kycStatus) {
                        0 -> {
                                itemView.clParent.setBackgroundResource(R.drawable.btn_blue_login)
                                itemView.ivCheck.invisible()
                        }
                        1 -> {
                                itemView.clParent.setBackgroundResource(R.drawable.bg_item_transaction_documents_approve)
                                itemView.ivCheck.visible()
                                itemView.ivCheck.setImageResource(R.drawable.ic_approve)
                        }
                        2, 4 -> {
                                itemView.clParent.setBackgroundResource(R.drawable.bg_item_transaction_documents_pending)
                                itemView.ivCheck.visible()
                                itemView.ivCheck.setImageResource(R.drawable.ic_pending)
                        }
                        3, 5 -> {
                                itemView.clParent.setBackgroundResource(R.drawable.bg_item_transaction_documents_declined)
                                itemView.ivCheck.visible()
                                itemView.ivCheck.setImageResource(R.drawable.ic_declined)
                        }
                }
                itemView.clParent.setOnClickListener {
                        when (item.kycStatus) {
                                0, 3, 5 -> onItemClick.invoke(item)
                        }
                }
        }
}