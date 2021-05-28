package com.tallin.wallet.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.tallin.wallet.R
import kotlinx.android.synthetic.main.circle_balance.view.*

class CircleBalanceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val TAG = CircleBalanceView::class.java.name

    var rate: Double = 0.0
        set(value) {
            field = value
            onUpdateUIState()
        }
    var balance: Double = 0.0
        set(value) {
            field = value
            onUpdateUIState()
        }
    var fiatSymbol: String = ""
        set(value) {
            field = value
            onUpdateUIState()
        }
    var cryptoName: String = "btc"
        set(value) {
            field = value
            onUpdateUIState()
        }

    init {
        inflate(context, R.layout.circle_balance, this)
    }

    private fun onUpdateUIState() {
        tvBalanceCrypto.text = String.format("%.8f", balance)
        tvNameCrypto.text = cryptoName
        var fiatBalance = String.format("%.2f", (balance * rate))
        fiatBalance = "$fiatSymbol$fiatBalance"
        tvBalanceFiat.text = fiatBalance
    }


}