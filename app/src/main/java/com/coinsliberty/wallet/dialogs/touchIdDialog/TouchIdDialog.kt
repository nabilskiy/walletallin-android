package com.coinsliberty.wallet.dialogs.touchIdDialog


import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.biometric.BiometricHelper
import kotlinx.android.synthetic.main.dialog_touch_id.*
import org.koin.android.viewmodel.ext.android.viewModel


class TouchIdDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_touch_id
    override val viewModel: TouchIdViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        icoId.setOnClickListener {
            BiometricHelper.createBiometricPrompt(this, biometricCallBack).authenticate(
                BiometricHelper.buildDefaultBiometricDialog(
                    "Authorization",
                    "Confirm login",
                    "Cancel"
                ))
        }

        tvOrUseMPin.setOnClickListener {
            dismiss()
        }
    }

    fun initListeners(onSuccess: (Boolean) -> Unit) {
        listener = onSuccess
    }

    private val biometricCallBack = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            listener?.invoke(true)
            dismiss()
        }
    }

    companion object {
        val TAG: String = TouchIdDialog::class.java.name
        fun newInstance(): TouchIdDialog {
            val fragment = TouchIdDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}