package com.tallin.wallet.utils.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> {
    private val liveData =   MutableLiveData<Event<T>>()

    var value: T? = null
        get() = liveData.value?.peekContent()

    fun postValue(value: T) {
        liveData.postValue(Event(value))
    }

    fun observe(owner: LifecycleOwner, observer: EventObserver<T>){
        liveData.observe(owner, observer)
    }

    fun observe(owner: LifecycleOwner, observer:(value: T)-> Unit) {
        liveData.observe(owner, EventObserver { observer(it) })
    }

}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}