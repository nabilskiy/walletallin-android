package com.example.coinsliberty.ui.wallet

import android.view.View
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.ui.wallet.data.WalletContent
import kotlinx.android.synthetic.main.item_wallet.view.*

class MyWalletHolder(private val onItemClick: (WalletContent) -> Unit) : Holder<WalletContent>() {
    override fun bind(itemView: View, item: WalletContent) {
//        itemView.itemWalletLayout.setBackgroundResource(item.itemBackground)
        itemView.ivItemWalletIcon.setImageResource(item.ico)
        itemView.tvItemWalletTitle.setText(item.title)
        itemView.tvItemWalletType.setText(item.type)
        if (item.price == null || item.result == null){
            itemView.tvItemWalletPrice.text = "Coming Soon"
        } else {
            itemView.tvItemWalletPrice.setText(item.price)
            itemView.tvItemWalletResult.setText(item.result)
        }
        itemView.rootView.setOnClickListener { onItemClick.invoke(item) }
    }
}

class TransactionHolder() : Holder<Unit>() {
    override fun bind(itemView: View, item: Unit) {

    }
}
