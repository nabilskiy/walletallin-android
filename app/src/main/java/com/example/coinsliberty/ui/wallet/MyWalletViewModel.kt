package com.example.coinsliberty.ui.wallet
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.ui.wallet.data.WalletContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyWalletViewModel(private val app: Application, private val repository: WalletRepository): BaseViewModel(app) {
    val walletLiveData: MutableLiveData<List<WalletContent>> = MutableLiveData()

//    fun getListData(): ArrayList<WalletContent> {
//        val listData: ArrayList<WalletContent> = ArrayList()
//        listData.add(WalletContent(R.drawable.ic_bitcoin, R.string.bitcoin_wallet, R.string.bitcoin_abreviatoure, R.string.bitcoin_price, R.string.bitcoin_result, R.color.lightOrange))
//        return listData
//    }
//
    fun walletList() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.walletList()
            Log.e("!!!", response.toString())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }


}