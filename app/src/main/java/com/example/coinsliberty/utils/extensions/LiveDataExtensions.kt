package com.example.coinsliberty.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.coinsliberty.ui.config.NavigationConfig
import kotlin.reflect.KFunction1

fun <T> LifecycleOwner.bindDataTo(liveData: LiveData<T>?, action: (T) -> Unit) {
    liveData?.observe(this, Observer { value ->
        value?.let{ action.invoke(it) }
    })
}