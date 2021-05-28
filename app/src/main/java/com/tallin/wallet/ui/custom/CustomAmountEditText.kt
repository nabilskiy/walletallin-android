package com.tallin.wallet.ui.custom

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.tallin.wallet.data.response.SwapLimitsData
import com.tallin.wallet.ui.exchange.ExchangeFragment
import java.lang.Exception

class CustomAmountEditText(private val editText: AppCompatEditText) {


    private val TAG = ExchangeFragment::class.java.name

    private val colorError = Color.parseColor("#FF2507")
    private var needToUpdateSwitch: Boolean = false

    var onAmountChangedListener: OnEditTextAmountChangeListener? = null
    var underline: Boolean = false
        set(value) {
            field = value
            updateUiState()
        }
    var walletColor = Color.parseColor("#f2a900")
        set(value) {
            field = value
            updateUiState()
        }
    var amount: Double = 0.0

    var balance: Double? = null
        set(value) {
            needToUpdateSwitch = false
            field = value
            updateUiState()
        }
    var limits: SwapLimitsData? = null
        set(value) {
            field = value
            updateUiState()
        }


    fun updateAmount(value: Double, needToUpdateSwitch: Boolean) {
        amount = value
        this.needToUpdateSwitch = needToUpdateSwitch
        editText.text = value.toString().toEditable()
        updateUiState()
    }


    private fun updateUiState() {
        Log.i(TAG, "updateUiState: ")
        val isValid = validAmount()
        val underlineColor = getUnderlineColor(isValid)
        if (underlineColor != null)
            editText.backgroundTintList = underlineColor
        editText.setTextColor(
            if (isValid)
                walletColor
            else colorError
        )
    }

    fun validAmount(): Boolean {
        Log.i(TAG, "validAmount: ${checkBalance()} ${checkLimits()}")
        return checkBalance() && checkLimits()
    }

    private fun checkBalance(): Boolean {
        balance ?: return true
        Log.i(TAG, "checkBalance: $balance $amount ${balance!! > amount}")
        return amount <= balance!!
    }

    private fun checkLimits(): Boolean {
        limits ?: return true
        Log.i(
            TAG,
            "checkLimits: $amount ${limits!!.minFrom} ${limits!!.maxFrom} ${amount in limits!!.minFrom..limits!!.maxFrom}"
        )
        limits ?: return false
        Log.i(
            TAG,
            "checkLimits:  ${amount * limits!!.rate} ${limits!!.minTo} ${limits!!.maxTo} ${amount <= limits!!.maxTo}"
        )
        return amount in limits!!.minFrom..limits!!.maxFrom
                && ((amount * limits!!.rate) <= limits!!.maxTo)
    }


    fun initListeners() {
        editText.addTextChangedListener(textChangeListener)
    }


    private fun getUnderlineColor(isValid: Boolean): ColorStateList? {
        if (!underline) return null
        if (isValid) {
            return ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(walletColor)
            )
        } else return ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(colorError)
        )

    }


    private val textChangeListener =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged: ")
                try {
                    amount = p0.toString().toDouble()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
                updateUiState()
                onAmountChangedListener?.onChanged(toDouble(), needToUpdateSwitch)
                editText.setSelection(p0?.length ?: 0)
                needToUpdateSwitch = true
            }
        }

    private fun toDouble(): Double {
        return try {
            editText.text.toString().replace(",", ".").toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
            0.0
        }
    }

//    init {
//        underline = try {
//            attributeSet?.getAttributeValue(R.attr.background) != null
//        } catch (e: Exception) {
//            false
//        }
//        initListeners()
//    }


    interface OnEditTextAmountChangeListener {
        fun onChanged(amount: Double, needToUpdateSwitch: Boolean)
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}