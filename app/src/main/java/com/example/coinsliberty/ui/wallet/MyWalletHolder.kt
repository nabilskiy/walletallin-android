package com.example.coinsliberty.ui.wallet

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.example.coinsliberty.R
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.ui.wallet.data.WalletContent
import kotlinx.android.synthetic.main.item_wallet.view.*

class MyWalletHolder(private val onItemClick: (WalletContent) -> Unit) : Holder<WalletContent>() {
    @SuppressLint("ResourceAsColor")
    override fun bind(itemView: View, item: WalletContent) {
        itemView.itemWalletLayout.setBackgroundResource(item.itemBackground)
        itemView.ivItemWalletIcon.setImageResource(item.ico)
        itemView.tvItemWalletTitle.setText(item.title)
        itemView.tvItemWalletType.setText(item.type)
        if (item.price == null){
            itemView.tvItemWalletPrice.setText("Coming Soon")
            itemView.tvItemWalletPrice.setTextColor(Color.GRAY)
        } else {
            itemView.tvItemWalletPrice.setText(item.price!!)
            itemView.tvItemWalletResult.setText(item.result!!)
        }
    }
}
