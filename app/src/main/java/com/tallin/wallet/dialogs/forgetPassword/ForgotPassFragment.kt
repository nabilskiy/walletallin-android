package com.tallin.wallet.dialogs.forgetPassword

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_forgot_pass.btnSentToMail
import kotlinx.android.synthetic.main.dialog_forgot_pass.forgotPasswordMail
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ForgotPassFragment : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_forgot_pass
    override val viewModel: ForgotPassViewModel by viewModel()
    override val navigator: ForgotPassNavigator = get()

    var listener: ((Boolean) -> Unit)? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSentToMail.setOnClickListener {
            viewModel.forgotPass(forgotPasswordMail.getMyText())
        }
        ivBack.setOnClickListener { goBack() }
        bindDataTo(viewModel.resultRecovery, ::getDialogError)
        forgotPasswordMail.showKeyboard()
    }

    override fun onPause() {
        super.onPause()
        forgotPasswordMail.hideKeyboard()
    }


    private fun getDialogError(signUpResponse: SignUpResponse?) {
        if (signUpResponse?.result == true) {
            goBack()
        }
    }

    fun goBack() {
        activity?.onBackPressed()
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = ForgotPassFragment::class.java.name
    }
}