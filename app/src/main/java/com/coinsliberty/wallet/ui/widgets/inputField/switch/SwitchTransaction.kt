package com.coinsliberty.wallet.ui.widgets.inputField.switch

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.coinsliberty.wallet.R
import kotlinx.android.synthetic.main.switch_transaction.view.*

class SwitchTransaction @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var listener: ((Boolean) -> Unit)? = null
    var checkListener: Boolean? = null

    init {
        initView()
       // readAttributes(attrs)
    }

    private fun initView() {
        inflate(context, R.layout.switch_transaction , this)
    }

    @SuppressLint("CustomViewStyleable")
    private fun readAttributes(attrs: AttributeSet?) {
        attrs ?: return

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SwitchComponent, 0, 0)

        val leftButtonText = typedArray.getString(R.styleable.SwitchComponent_sc_left_btn_text) ?: ""

        val rightButtonText = typedArray.getString(R.styleable.SwitchComponent_sc_right_btn_text) ?: ""

        val switchStatus = typedArray.getBoolean(R.styleable.SwitchComponent_sc_status, false)

        setSwitchStyle(leftButtonText, rightButtonText)
        changeStatus(switchStatus)
        btnStatusListener()

        typedArray.recycle()
    }

    fun setSwitchStyle(leftBtnText: String, rightBtnText: String){
        tvTextLeft.text = leftBtnText
        tvTextRight.text = rightBtnText
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    @SuppressLint("ResourceAsColor")
    fun changeStatus(status: Boolean){
        checkListener = status
        if(status){
            tvTextLeft.isActivated = true
            tvTextRight.isActivated = false
            btnLeft.setBackgroundResource(R.drawable.btn_switch_transaction)
            btnRight.background = null

        } else {
            tvTextLeft.isActivated = false
            tvTextRight.isActivated = true
            btnLeft.background = null
            btnRight.setBackgroundResource(R.drawable.btn_switch_transaction)
        }
    }

    fun btnStatusListener(){
        btnLeft.setOnClickListener {
            when (checkListener){
                true -> return@setOnClickListener
                false -> {
                    checkListener = true
                    listener?.invoke(true)
                    changeStatus(true)
                }
            }
        }
        btnRight.setOnClickListener {
            when (checkListener){
                false -> return@setOnClickListener
                true -> {
                    checkListener = false
                    listener?.invoke(false)
                    changeStatus(false)
                }
            }
        }
    }

}