package com.example.coinsliberty.dialogs


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel

class ForgotPassDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_forgot_pass
    override val viewModel: StubViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    companion object {
        val TAG: String = ForgotPassDialog::class.java.name
        fun newInstance(): DialogFragment {
            val fragment = ForgotPassDialog()
            //fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}