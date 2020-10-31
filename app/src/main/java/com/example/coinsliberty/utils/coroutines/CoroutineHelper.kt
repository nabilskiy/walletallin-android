package com.example.coinsliberty.utils.coroutines

import android.content.Context
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutineHelper(private val scope: CoroutineScope) {

    fun launch(checkInternetConnection: Boolean = true,
               coroutineContext: CoroutineContext = Dispatchers.IO,
               context: Context,
               block: suspend CoroutineScope.() -> Unit,
               onError: (e: Throwable) -> Unit
    )  = scope.launch(coroutineContext + ExceptionHandler(onError)) {
//        if (checkInternetConnection) checkInternetConnection(context)

        block()
    }

    inner class ExceptionHandler(private val onError : (Throwable) -> Unit) :
        CoroutineExceptionHandler {
        override val key: CoroutineContext.Key<*>
            get() = CoroutineExceptionHandler.Key

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            scope.launch(Dispatchers.Main) { onError(exception) }
        }
    }


    fun startTimer(timer: Int, block: suspend CoroutineScope.(timerResponse: TimerResult) -> Unit) {
        scope.launch {
            repeat(timer + 1) {
                block(TimerResult(timer, timer - it, it >= timer))
                delay(1_000)
            }
        }
    }
}