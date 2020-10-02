package com.example.coinsliberty.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.bindDataTo(liveData: LiveData<T>?, action: (T) -> Unit) {
    liveData?.observe(this, Observer { value ->
        value?.let{ action.invoke(it) }
    })
}