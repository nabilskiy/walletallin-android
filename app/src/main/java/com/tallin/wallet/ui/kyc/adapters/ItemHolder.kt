package com.tallin.wallet.ui.kyc.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.Holder
import com.tallin.wallet.data.response.KycDocuments
import kotlinx.android.synthetic.main.item_kyc.view.*

class ItemHolder(private val onItemClick: (String?, String?, Int?, Int?) -> Unit, private  val context: Context?) : Holder<KycDocuments>() {
    @SuppressLint("ResourceType")
    override fun bind(itemView: View, item: KycDocuments) {

        itemView.tvTitle.text = item.name
        itemView.tvDescription.text = item.description
        itemView.btnAction.text = item.type

        if (context != null) {
            val statusColor: Int
            val statusText: String
            when (item.status) {
                0 -> {
                    statusText = context.getString(R.string.not_start_verify)
                    statusColor = ifM(R.color.not_start_verify)
                }
                1 -> {
                    statusText = context.getString(R.string.approved)
                    statusColor = ifM(R.color.approved)
                }
                2 -> {
                    statusText = context.getString(R.string.start_verify)
                    statusColor = ifM(R.color.start_verify)
                }
                3 -> {
                    statusText = context.getString(R.string.error_verify)
                    statusColor = ifM(R.color.error_verify)
                }
                4 -> {
                    statusText = context.getString(R.string.needs_review)
                    statusColor = ifM(R.color.needs_review)
                }
                5 -> {
                    statusText = context.getString(R.string.declined)
                    statusColor = ifM(R.color.declined)
                }
                9 -> {
                    statusText = context.getString(R.string.unknown)
                    statusColor = ifM(R.color.unknown)
                }
                else -> {
                    statusText = context.getString(R.string.unknown)
                    statusColor = ifM(R.color.unknown)
                }
            }

            itemView.tvStatusText.text = statusText
            itemView.ivStatusEllipseColor.setColorFilter(statusColor)
            itemView.ivStatusShield.setColorFilter(statusColor)
        } else {
            println("context: $context | version: ${Build.VERSION.SDK_INT}")
        }
        itemView.btnAction.setOnClickListener { onItemClick.invoke(item.type, item.flowName, item.id, item.docId) }
    }

    fun ifM(res: Int) : Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            context?.getColor(res) ?: 0xb3b3b3 else context?.resources?.getInteger(res) ?: 0xb3b3b3
    }
}