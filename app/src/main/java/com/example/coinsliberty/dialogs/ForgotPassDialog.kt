package com.example.coinsliberty.dialogs

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
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

            if (viewModel.resultRecovery.value == false) {
                ErrorDialog.newInstance(viewModel.messageRecovery.value.toString())
                    .show(parentFragmentManager, ErrorDialog.TAG)
            }
        }
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = ForgotPassDialog::class.java.name
        fun newInstance(): ForgotPassDialog {
            val fragment = ForgotPassDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}