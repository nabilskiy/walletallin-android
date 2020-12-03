 package com.coinsliberty.wallet.ui.wallet.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.Holder
import com.coinsliberty.wallet.data.response.TransactionItem
import com.coinsliberty.wallet.ui.wallet.data.WalletContent
import com.coinsliberty.wallet.utils.convertTimestampForUI
import com.coinsliberty.wallet.utils.extensions.gone
import com.coinsliberty.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.item_data.view.*
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import kotlinx.android.synthetic.main.item_wallet.view.*
import java.io.File.separator


 class MyWalletHolder(private val onItemClick: (WalletContent) -> Unit) : Holder<WalletContent>() {
    override fun bind(itemView: View, item: WalletContent) {
        itemView.itemWalletLayout.setBackgroundResource(item.itemBackground)
        itemView.ivItemWalletIcon.setImageResource(item.ico)
        itemView.tvItemWalletTitle.text = item.title
        itemView.tvItemWalletType.text = item.type
        if (item.price == null || item.result == null){
            itemView.tvItemWalletPrice.setTypeface(itemView.tvItemWalletPrice.typeface, Typeface.BOLD_ITALIC)
            itemView.tvItemWalletPrice.setTextColor(itemView.context.resources.getColor(R.color.grey))
            itemView.tvItemWalletPrice.text = "Coming Soon"
        } else {
            itemView.tvItemWalletPrice.text = item.price
            itemView.tvItemWalletResult.text = item.result
            itemView.rootView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}

class TransactionDataHolder() : Holder<Long>() {
    override fun bind(itemView: View, item: Long) {
        itemView.tvData.text = convertTimestampForUI(item)
    }
}

class TransactionTitleHolder() : Holder<String>() {
    override fun bind(itemView: View, item: String) {
        itemView.tvTitle.text = item
    }
}

class TransactionHolder() : Holder<TransactionItem>() {
    override fun bind(itemView: View, item: TransactionItem) {
        when(item.typeItem) {
            0 -> {
                itemView.rootView.background = itemView.context.resources.getDrawable(R.drawable.bg_transaction_top)
                itemView.separator.visible()
            }
            1 -> {
                itemView.rootView.background = itemView.context.resources.getDrawable(R.drawable.bg_transaction_middle)
                itemView.separator.visible()
            }
            2 -> {
                itemView.rootView.background = itemView.context.resources.getDrawable(R.drawable.bg_transaction_bottom)
                itemView.separator.gone()
            }
        }

        itemView.ivIcon.setImageResource(if(item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvType.text = if(item.category != "send") "Sent" else "Received"
        itemView.tvPrice.text = item.amountUsd + " $"
        itemView.ivOpenIcon.setImageResource(if(item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvOpenType.text = if(item.category != "send") "Sent" else "Received"
        itemView.tvOpenPrice.text =  item.amount + " BTC\n" + item.amountUsd + " $"
        itemView.tvOpenWalletAddress.text = "Wallet Address: " + item.address

        itemView.tvOpenBlockchainList.isClickable = true;
        itemView.tvOpenBlockchainList.movementMethod = LinkMovementMethod.getInstance()
        itemView.tvOpenBlockchainList.text = Html.fromHtml("<a href='https://www.blockchain.com/btc/tx/${item.txid}'>" + item.txid + "</a>")
        itemView.clFull.setOnClickListener {
            itemView.clFull.gone()
            itemView.clSample.visible()
        }
        itemView.clSample.setOnClickListener {
            itemView.clSample.gone()
            itemView.clFull.visible()
        }

        itemView.ivOpenBlockchainCopy.setOnClickListener { copyLink(itemView.rootView.context, "https://www.blockchain.com/btc/tx/${item.txid}") }
    }

    private fun copyLink(context: Context, copyText: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", copyText)
        clipboard.setPrimaryClip(clip)
        toast("Copy", context)
    }

    fun toast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
