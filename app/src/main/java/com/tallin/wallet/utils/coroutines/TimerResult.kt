package com.tallin.wallet.utils.coroutines

data class TimerResult(val totalTime: Int,
                       val timeLeft: Int,
                       val isFinished: Boolean)