package com.tallin.wallet.ui.wallet.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tallin.wallet.R
import com.tallin.wallet.base.Holder
import com.tallin.wallet.data.response.TransactionItem
import com.tallin.wallet.ui.wallet.data.WalletContent
import com.tallin.wallet.utils.convertTimestampForUI
import com.tallin.wallet.utils.currency.Currency
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.invisible
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.item_data.view.*
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import kotlinx.android.synthetic.main.item_wallet.view.*


class MyWalletHolder(private val onItemClick: (WalletContent) -> Unit) : Holder<WalletContent>() {
    override fun bind(itemView: View, item: WalletContent) {
//        itemView.itemWalletLayout.setBackgroundResource(item.itemBackground)
        itemView.ivItemWalletIcon.setImageResource(item.ico)
        itemView.tvItemWalletTitle.text = item.title
        itemView.tvItemWalletType.text = "(${item.type})"
        if (item.price == null || item.result == null) {
            itemView.tvItemWalletPrice.setTypeface(
                itemView.tvItemWalletPrice.typeface,
                Typeface.BOLD_ITALIC
            )
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

class TransactionHolder(private var listener: (Int) -> Unit) : Holder<TransactionItem>() {
    override fun bind(itemView: View, item: TransactionItem) {

        Log.e("!!!", item.currency.toString())
//        when (item.typeItem) {
//            0 -> {
//                itemView.rootView.background =
//                    itemView.context.resources.getDrawable(R.drawable.bg_transaction_top)
//                itemView.separator.visible()
//            }
//            1 -> {
//                itemView.rootView.background =
//                    itemView.context.resources.getDrawable(R.drawable.bg_transaction_middle)
//                itemView.separator.visible()
//            }
//            2 -> {
//                itemView.rootView.background =
//                    itemView.context.resources.getDrawable(R.drawable.bg_transaction_bottom)
//                itemView.separator.gone()
//            }
//        }

        itemView.vCheckClick.setOnClickListener {
            item.num?.let {
                listener.invoke(it)
            }
        }

        itemView.ivIcon.setImageResource(if (item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvAmout.text = "${item.amountUsd} ${item.currency.getTitle()}"
        itemView.tvCrypto.text = "BTC" //todo
        when(item.compliance){
            0, 1, 2 -> {
                itemView.tvCheck.visible()
                itemView.ivCheck.invisible()
            }
            null -> {
                itemView.tvCheck.gone()
                itemView.ivCheck.visible()
            }
        }
        //if (item.category == "send") "Sent" else "Received"
        val colorBlue = Color.parseColor("#4C7BF6")
        val colorGreen = Color.parseColor("#00D983")
        itemView.tvAmout.setTextColor(
            if (item.category == "send")
                colorBlue
            else colorGreen
        )
        itemView.tvOpenPrice.setTextColor(
            if (item.category == "send")
                colorBlue
            else colorGreen
        )
       /* itemView.tvPrice.text =
            item.amountUsd + if (item.currency == null || item.currency == Currency.USD) " $" else " €"*/
        itemView.ivOpenIcon.setImageResource(if (item.category == "send") R.drawable.ic_send_icon else R.drawable.ic_arrow_left)
        itemView.tvOpenType.text = if (item.category != "send") "Sent" else "Received"
        itemView.tvOpenPrice.text =
            item.amount + " BTC\n" + item.amountUsd + if (item.currency == null || item.currency == Currency.USD) " $" else " €"
        itemView.tvOpenWalletAddress.text = "Wallet Address: " + item.address

        itemView.tvOpenBlockchainList.isClickable = true;
        itemView.tvOpenBlockchainList.movementMethod = LinkMovementMethod.getInstance()
        itemView.tvOpenBlockchainList.text =
            Html.fromHtml("<a href='https://www.blockchain.com/btc/tx/${item.txid}'>" + item.txid + "</a>")
        itemView.clFull.setOnClickListener {
            itemView.clFull.gone()
            itemView.clSample.visible()
        }
        itemView.clSample.setOnClickListener {
            itemView.clSample.gone()
            itemView.clFull.visible()
        }

        itemView.ivOpenBlockchainCopy.setOnClickListener {
            copyLink(
                itemView.rootView.context,
                "https://www.blockchain.com/btc/tx/${item.txid}"
            )
        }
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
