package com.example.coinsliberty.dialogs.ressPassword


import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_reset_pass.*


class ResetPassDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_reset_pass
    override val viewModel: ResetPassViewModel by viewModel()

    var listener: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivClose.setOnClickListener { dismiss() }
        btnUpdate.setOnClickListener {
            if (checkPass(newPass = newPass.getMyText(), confirmPass = confirmPass.getMyText())) {
                viewModel.changePassword(
                    oldPassword = oldPass.getMyText(),
                    password = newPass.getMyText()
                )
            } else {
                getErrorDialog()
            }
            //listener?.invoke(true)
        }
        bindDataTo(viewModel.timeToDismiss, ::dismissThisDialog)
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    private fun checkPass(newPass: String, confirmPass: String): Boolean {
        return newPass == confirmPass
    }

    private fun getErrorDialog() {
        ErrorDialog.newInstance("New password does not match")
            .show(parentFragmentManager, ErrorDialog.TAG)
    }

    fun dismissThisDialog(boolean: Boolean) {
        if (boolean) {
            dismiss()
        }
    }

    companion object {
        val TAG: String = ResetPassDialog::class.java.name
        fun newInstance(): ResetPassDialog {
            val fragment = ResetPassDialog()
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}