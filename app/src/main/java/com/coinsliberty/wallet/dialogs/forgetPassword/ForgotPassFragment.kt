package com.coinsliberty.wallet.dialogs.forgetPassword

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_forgot_pass.btnSentToMail
import kotlinx.android.synthetic.main.dialog_forgot_pass.forgotPasswordMail
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import kotlinx.android.synthetic.main.switch_echange.*
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