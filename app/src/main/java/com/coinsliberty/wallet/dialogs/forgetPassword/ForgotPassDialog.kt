package com.coinsliberty.wallet.dialogs.forgetPassword

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_forgot_pass.*

class ForgotPassDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_forgot_pass
    override val viewModel: ForgotPassViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSentToMail.setOnClickListener {
            viewModel.forgotPass(forgotPasswordMail.getMyText())
        }
        bindDataTo(viewModel.resultRecovery, ::getDialogError)
    }

    private fun getDialogError(signUpResponse: SignUpResponse?) {
        if(signUpResponse?.result == true) {
            dismiss()
        }
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = ForgotPassDialog::class.java.name
        fun newInstance(): ForgotPassDialog {
            val fragment = ForgotPassDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGrayBG)
            return fragment
        }
    }
}