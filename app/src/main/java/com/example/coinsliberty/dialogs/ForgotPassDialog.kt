package com.example.coinsliberty.dialogs


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_forgot_pass.*

class ForgotPassDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_forgot_pass
    override val viewModel: StubViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSentToMail.setOnClickListener {
            listener?.invoke(forgotPasswordMail.getMyText().isNotEmpty())
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