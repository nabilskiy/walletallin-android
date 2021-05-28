package com.tallin.wallet.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tallin.wallet.utils.liveData.EventObserver
import com.tallin.wallet.utils.liveData.SingleLiveData

fun <T> LifecycleOwner.bindDataTo(liveData: LiveData<T>?, action: (T) -> Unit) {
    liveData?.observe(this, Observer { value ->
        value?.let{ action.invoke(it) }
    })
}

fun <T> LifecycleOwner.bindDataTo(liveData: SingleLiveData<T>?, action: (T) -> Unit) {
    liveData?.observe(this, EventObserver { value ->
        value?.let{ action.invoke(it) }
    })
}