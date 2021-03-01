package com.coinsliberty.wallet.ui.exchange

import androidx.lifecycle.LiveData
import com.coinsliberty.wallet.data.response.AvailableBalanceInfoContent

class AvailableBalanceLiveData : LiveData<AvailableBalanceLiveData.AvailableBalanceLiveDataData>() {

    fun setValue(balance: AvailableBalanceInfoContent?, reverse: Boolean) {
        if (reverse)
            postValue(AvailableBalanceLiveDataData(balance?.eth, balance?.btc))
        else
            postValue(AvailableBalanceLiveDataData(balance?.btc, balance?.eth))
    }


    fun swap() {
        value?.balanceFrom = value?.balanceTo.also { value?.balanceTo = value?.balanceFrom }
        postValue(value)
    }

    data class AvailableBalanceLiveDataData(
        var balanceFrom: Double?,
        var balanceTo: Double?
    )
}