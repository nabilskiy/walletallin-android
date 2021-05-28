package com.tallin.wallet.ui.exchange

import androidx.lifecycle.LiveData
import com.tallin.wallet.data.response.AvailableBalanceInfoContent

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

    fun setFrom(balance: Double) {
        value?.balanceFrom = balance
        postValue(value)
    }

    fun setTo(balance: Double) {
        value?.balanceTo = balance
        postValue(value)
    }


    data class AvailableBalanceLiveDataData(
        var balanceFrom: Double?,
        var balanceTo: Double?
    )
}