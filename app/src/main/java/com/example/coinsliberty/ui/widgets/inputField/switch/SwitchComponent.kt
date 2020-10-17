package com.example.coinsliberty.ui.widgets.inputField.switch

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.coinsliberty.R
import kotlinx.android.synthetic.main.switch_component.view.*

class SwitchComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initView()
        readAttributes(attrs)
    }

    private fun initView() {
        inflate(context, R.layout.switch_component , this)
    }

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
        switchLeftButton.text = leftBtnText
        switchRightButton.text = rightBtnText
    }

    fun changeStatus(status: Boolean){
        if(status){
            switchLeftButton.isActivated = true
            switchRightButton.isActivated = false
        } else {
            switchLeftButton.isActivated = false
            switchRightButton.isActivated = true
        }
    }

    fun btnStatusListener(){
        switchLeftButton.setOnClickListener {
            changeStatus(true)
        }
        switchRightButton.setOnClickListener {
            changeStatus(false)
        }
    }

}