package com.coinsliberty.wallet.dialogs.faceIdDialog

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricPrompt
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.biometric.BiometricHelper
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_face_id.*
import org.koin.android.viewmodel.ext.android.viewModel


class FaceIdDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_face_id
    override val viewModel: FaceIdViewModel by viewModel()

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
        val TAG: String = FaceIdDialog::class.java.name
        fun newInstance(): FaceIdDialog {
            val fragment = FaceIdDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}