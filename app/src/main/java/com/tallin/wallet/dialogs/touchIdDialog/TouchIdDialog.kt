package com.tallin.wallet.dialogs.touchIdDialog


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.biometric.BiometricPrompt
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinDialogFragment
import com.tallin.wallet.utils.biometric.BiometricHelper
import kotlinx.android.synthetic.main.dialog_touch_id.*
import org.koin.android.viewmodel.ext.android.viewModel


class TouchIdDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_touch_id
    override val viewModel: TouchIdViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    var checkBiometric: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = dialog!!.window

        window!!.setGravity(Gravity.BOTTOM)
        val params = window.attributes
        params.y = 100
        window.attributes = params

        dialog?.setOnDismissListener {
            listener?.invoke(checkBiometric)
            Log.e("!!!", "test")
        }

        BiometricHelper.createBiometricPrompt(this, biometricCallBack).authenticate(
            BiometricHelper.buildDefaultBiometricDialog(
                "Authorization",
                "Confirm login",
                "Cancel"
            ))

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
            checkBiometric = true
            listener?.invoke(true)
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        listener?.invoke(false)
        super.onDismiss(dialog)
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