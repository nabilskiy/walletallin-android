package com.tallin.wallet.ui.widgets.inputField.input

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.tallin.wallet.R
import com.tallin.wallet.utils.extensions.visibleIfOrGone
import kotlinx.android.synthetic.main.input_field.view.*

@Suppress("PLUGIN_WARNING")
class InputFieldComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var showIcon: Boolean = true

    enum class TypeOfInput {
        textPassword, phone, textEmailAddress, text, textNameBusiness, phoneWithIco, phoneMobileWithIco,
        textWebSite, textLocation, numPostCode, textDescription
    }

    init {
        initView()
        readAttributes(attrs)
    }

    private fun initView() {
        inflate(context, R.layout.input_field, this)
        showHideData()
    }

    fun showKeyboard() {
        etField.requestFocus()
        val imgr: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.showSoftInput(etField, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val imgr: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun readAttributes(attrs: AttributeSet?) {
        attrs ?: return

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.InputFieldComponent, 0, 0)

        val header = typedArray.getString(R.styleable.InputFieldComponent_ifc_header) ?: ""
        tvInputHeader.text = header

        val typeOfInput = typedArray.getString(R.styleable.InputFieldComponent_ifc_type) ?: "text"

        showIcon = typedArray.getBoolean(R.styleable.InputFieldComponent_ifc_show_icon, true)

        val useMask = typedArray.getBoolean(R.styleable.InputFieldComponent_ifc_use_mask, false)
        cbShowHide.visibleIfOrGone { useMask }

        val showMask = typedArray.getBoolean(R.styleable.InputFieldComponent_ifc_mask, true)
        cbShowHide.isChecked = showMask

        currentInputType(typeOfInput)
        typedArray.recycle()
    }


    private fun showHideData() {
        cbShowHide.setOnCheckedChangeListener { _, isChecked ->
            etField.apply {
                transformationMethod = PasswordTransformationMethod().takeIf { isChecked.not() }

                val size = text?.length ?: 0
                if (size > 0) setSelection(size)
            }
        }
    }

    private fun currentInputType(type: String) {
        cbShowHide.visibility = View.INVISIBLE

        when (type) {
            TypeOfInput.phone.name -> setPhoneNumber()
            TypeOfInput.textEmailAddress.name -> {
                etField.inputType =
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_email_marketing,
                        0, 0, 0
                    )
            }
            TypeOfInput.textPassword.name -> {
                cbShowHide.visibility = View.VISIBLE
                cbShowHide.isChecked = false
                etField.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_key,
                        0, 0, 0
                    )
            }
            TypeOfInput.text.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_user_copy,
                        0, 0, 0
                    )
            }
            TypeOfInput.textNameBusiness.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_business,
                        0, 0, 0
                    )
            }
            TypeOfInput.phoneWithIco.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_phone,
                        0, 0, 0
                    )
            }
            TypeOfInput.phoneMobileWithIco.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_phone_mobile,
                        0, 0, 0
                    )
            }
            TypeOfInput.textWebSite.name -> {
                etField.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_website,
                        0, 0, 0
                    )
            }
            TypeOfInput.textLocation.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_location,
                        0, 0, 0
                    )
            }
            TypeOfInput.numPostCode.name -> {
                etField.inputType = InputType.TYPE_CLASS_NUMBER
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_post_code,
                        0, 0, 0
                    )
            }
            TypeOfInput.textDescription.name -> {
                etField.inputType = InputType.TYPE_CLASS_TEXT
                if (showIcon)
                    etField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_description,
                        0, 0, 0
                    )
            }
        }
    }

    fun addTextWatcher(watcher: TextWatcher) {
        etField.addTextChangedListener(watcher)
    }

    private fun setPhoneNumber() {
        etField.inputType = InputType.TYPE_CLASS_NUMBER

        etField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                modifyNumber(s.toString())
            }
        })
    }

    fun getMyText() = etField.text.toString()

    fun setText(text: String) {
        etField.setText(text)
    }

    fun setTextColor(color: Int) {
        etField.setTextColor(color)
    }

    private fun modifyNumber(number: String) {
        if (number.isNotEmpty()) {
            if (!number.startsWith("+")) {
                etField.setText("+${number}")
                etField.setSelection(etField.text.length)
            }
        }
    }

}