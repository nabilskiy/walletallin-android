 package com.coinsliberty.wallet.ui.wallet.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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


 class MyWalletHolder(private val onItemClick: (WalletContent) -> Unit) : Holder<WalletContent>() {
    override fun bind(itemView: View, item: WalletContent) {
        itemView.itemWalletLayout.setBackgroundResource(item.itemBackground)
        itemView.ivItemWalletIcon.setImageResource(item.ico)
        itemView.tvItemWalletTitle.text = item.title
        itemView.tvItemWalletType.text = item.type
        if (item.price == null || item.result == null){
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
        itemView.ivIcon.setImageResource(if(item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvType.text = if(item.category != "send") "Deposite" else "Withrawal"
        itemView.tvPrice.text = item.amountUsd + " $"
        itemView.ivOpenIcon.setImageResource(if(item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvOpenType.text = if(item.category != "send") "Deposite" else "Withrawal"
        itemView.tvOpenPrice.text =  item.amount + " BTC\n" + item.amountUsd + " $"
        itemView.tvOpenWalletAddress.text = "Wallet Address: " + item.address
        itemView.clFull.setOnClickListener {
            itemView.clFull.gone()
            itemView.clSample.visible()
        }
        itemView.clSample.setOnClickListener {
            itemView.clSample.gone()
            itemView.clFull.visible()
            try{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.blockchain.com/btc/tx"))
                browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.rootView.context.startActivity(browserIntent)
            } catch (error: Error){
                Log.e("!!", error.toString())
            }

        }
        itemView.tvOpenWalletAddress.setOnClickListener { copyLink(itemView.rootView.context, item.address ?: "") }
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
