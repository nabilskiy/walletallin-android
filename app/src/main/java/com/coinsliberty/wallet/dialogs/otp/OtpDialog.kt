package com.coinsliberty.wallet.dialogs.otp

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_forgot_pass.*

class OtpDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_otp
    override val viewModel: StubViewModel by viewModel()

    var listener: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSentToMail.setOnClickListener {
            listener?.invoke(forgotPasswordMail.getMyText())
        }
    }

    fun initListeners(onChoosen: (String) -> Unit) {
        listener = onChoosen
    }

    companion object {
        val TAG: String = OtpDialog::class.java.name
        fun newInstance(): OtpDialog {
            val fragment = OtpDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGrayBG)
            return fragment
        }
    }
}