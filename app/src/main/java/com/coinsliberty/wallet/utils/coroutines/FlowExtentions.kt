package com.coinsliberty.wallet.utils.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private class Buffer<T> {
    private val list = arrayListOf<T>()

    @Synchronized
    fun add(value: T) { list.add(value) }

    @Synchronized
    fun pollIfNotEmpty() : List<T>? = list.takeIf { it.isNotEmpty() }?.toList()?.also { list.clear() }
}

fun <T> Flow<T>.timedBuffer(millis: Long): Flow<List<T>> = channelFlow {
    val buffer = Buffer<T>()

    suspend fun flush() = buffer.pollIfNotEmpty()?.let { send(it) }

    val job = launch {
        while (isActive) {
            delay(millis)
            flush()
        }
    }
    collect { buffer.add(it) }
//    job.cancelAndJoin()
//    flush()
}