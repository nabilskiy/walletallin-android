package com.example.moneybee.ui.widgets.inputField

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.example.coinsliberty.R
import com.example.coinsliberty.utils.extensions.visibleIfOrGone
import kotlinx.android.synthetic.main.input_field.view.*

@Suppress("PLUGIN_WARNING")
class InputFieldComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    enum class TypeOfInput {
        textPassword, phone, textEmailAddress, text
    }

    init {
        initView()
        readAttributes(attrs)
    }

    private fun initView() {
        inflate(context, R.layout.input_field , this)
        showHideData()
    }

    private fun readAttributes(attrs: AttributeSet?) {
        attrs ?: return

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InputFieldComponent, 0, 0)

        val header = typedArray.getString(R.styleable.InputFieldComponent_ifc_header) ?: ""
        tvInputHeader.text = header

        val typeOfInput = typedArray.getString(R.styleable.InputFieldComponent_ifc_type) ?: "text"


        val useMask = typedArray.getBoolean(R.styleable.InputFieldComponent_ifc_use_mask, false)
        cbShowHide.visibleIfOrGone { useMask }

        val showMask = typedArray.getBoolean(R.styleable.InputFieldComponent_ifc_mask, true)
        cbShowHide.isChecked = showMask

        currentInputType(typeOfInput)
        typedArray.recycle()
    }


    fun showHideData() {
        cbShowHide.setOnCheckedChangeListener { _, isChecked ->
            etField.apply {
                transformationMethod = PasswordTransformationMethod().takeIf { isChecked.not() }

                val size = text?.length ?: 0
                if (size > 0) setSelection(size)
            }
        }
    }

    fun currentInputType(type: String) {
        cbShowHide.visibility = View.INVISIBLE

        when (type) {
            TypeOfInput.phone.name -> setPhoneNumber()
            TypeOfInput.textEmailAddress.name -> etField.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            TypeOfInput.textPassword.name -> {
                cbShowHide.visibility = View.VISIBLE
                cbShowHide.isChecked = false
                etField.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
            }
            TypeOfInput.text.name -> etField.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    fun setPhoneNumber() {
        etField.inputType = InputType.TYPE_CLASS_NUMBER

        etField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                modifyNumber(s.toString())
            }
        })
    }

    fun getMyText() = etField.text.toString()

    private fun modifyNumber(number: String) {
        if (number.isNotEmpty()) {
            if (!number.startsWith("+")) {
                etField.setText("+${number}")
                etField.setSelection(etField.text.length)
            }
        }
    }

}