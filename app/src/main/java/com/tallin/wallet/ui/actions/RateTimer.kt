package com.tallin.wallet.ui.actions

import androidx.lifecycle.lifecycleScope
import com.tallin.wallet.R
import com.tallin.wallet.ui.actions.buy.BuySellFragment
import com.tallin.wallet.ui.actions.orderPreview.OrderPreviewFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RateTimer {

    var isRun = false
    var fragmentBuySell: BuySellFragment? = null
    var fragmentOrderPreview: OrderPreviewFragment? = null

    fun startTimer(time: Long = 60000) {
        if (isRun) return
        isRun = true
        val now = System.currentTimeMillis()
        val finish = now + time

        fragmentBuySell?.lifecycleScope?.launch(Dispatchers.IO) {
            try {
                while (finish > System.currentTimeMillis() && isRun) {
                    val rateInt = ((finish - System.currentTimeMillis()) / 1000).toInt()
                    val rateString = fragmentBuySell?.activity?.resources?.getString(
                        R.string.rate_available_for_sec,
                        rateInt
                    ) ?: ""
                    when (getCorrectFragment()) {
                        "BuySellFragment" -> {
                            fragmentBuySell?.whenRun(rateString)
                        }
                        "OrderPreviewFragment" -> {
                            fragmentOrderPreview?.whenRun(rateString, rateInt)
                        }
                        else -> {
                            val cnc = fragmentBuySell?.navController?.currentDestination?.label
                            println(cnc)
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(200)
                }
                isRun = false
                when (getCorrectFragment()) {
                    "BuySellFragment" -> {
                        fragmentBuySell?.whenFinish()
                    }
                    "OrderPreviewFragment" -> {
                        fragmentOrderPreview?.whenFinish()
                    }
                }
            } catch (e: Throwable) {
                isRun = false
                println(e)
            }
        }
    }
    private fun getCorrectFragment() = fragmentBuySell?.navController?.currentDestination?.label
}