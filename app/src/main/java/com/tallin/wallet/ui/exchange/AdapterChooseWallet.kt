package com.tallin.wallet.ui.exchange

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tallin.wallet.R
import com.tallin.wallet.ui.wallet.data.WalletContent
import kotlinx.android.synthetic.main.item_choose_wallet.view.*

class AdapterChooseWallet(
    private val wallets: MutableList<WalletContent>,
    private val onClickListener: WalletClickListener,
    private val chosenPosition: WalletContent
) :
    RecyclerView.Adapter<AdapterChooseWallet.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_choose_wallet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallet = wallets[position]
        if (wallet == chosenPosition)
            holder.check.visibility = View.VISIBLE
        holder.title.text = wallet.title
        holder.title.setTextColor(wallet.color)
        holder.type.text = wallet.type
        holder.img.setImageResource(wallet.ico)
    }

    override fun getItemCount(): Int {
        return wallets.size
    }

    inner class ViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        val title: TextView = itemView.tvItemWalletTitle
        val type: TextView = itemView.tvItemWalletType
        val img: ImageView = itemView.ivItemWalletIcon
        val check: ImageView = itemView.check

        init {
            rootView.setOnClickListener { onClickListener.onClick(wallets[adapterPosition]) }
        }

    }

    interface WalletClickListener {
        fun onClick(wallet: WalletContent)
    }
}