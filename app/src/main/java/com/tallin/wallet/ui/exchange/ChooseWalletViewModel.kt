package com.tallin.wallet.ui.exchange

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.ui.wallet.data.WalletContent
import java.util.*

class ChooseWalletViewModel(
    private val exchangeViewModel: ExchangeViewModel,
    private val isWalletFrom: Boolean
) {

    private val TAG = ChooseWalletViewModel::class.java.name
    val wallets =
        MutableLiveData<MutableList<WalletContent>>(
            (exchangeViewModel.walletLiveData.value?.toMutableList()
                ?: mutableListOf()) as MutableList<WalletContent>?
        )

    val currentWallet =
        if (isWalletFrom)
            exchangeViewModel.walletFrom.value
        else exchangeViewModel.walletTo.value


    fun search(str: String) {
        Log.i(TAG, "search: $str")
        if (str.isEmpty()) {
            Log.i(TAG, "search: is empty")
            wallets.value =
                exchangeViewModel.walletLiveData.value?.toMutableList() ?: mutableListOf()
            return
        }
        val tmpWallets = mutableListOf<WalletContent>()
        tmpWallets ?: return
        for (wallet in wallets.value!!.iterator()) {
            val type = wallet.type ?: ""
            val title = wallet.title ?: ""
            if (type.toLowerCase(Locale.ROOT).contains(str.toLowerCase(Locale.ROOT)) ||
                title.toLowerCase(Locale.ROOT).contains(str.toLowerCase(Locale.ROOT))
            ) {
                tmpWallets.add(wallet)
            }
        }
        Log.i(TAG, "search: $str ${tmpWallets.size}")
        wallets.value = tmpWallets
    }
}
