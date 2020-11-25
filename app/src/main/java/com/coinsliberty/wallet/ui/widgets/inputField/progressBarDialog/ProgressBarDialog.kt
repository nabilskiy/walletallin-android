package com.coinsliberty.wallet.ui.widgets.inputField.progressBarDialog

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_progress_bar.*
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProgressBarDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_progress_bar
    override val viewModel: StubViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }


    companion object {
        val TAG: String = ProgressBarDialog::class.java.name
        fun newInstance(): DialogFragment {
            val fragment = ProgressBarDialog()
            return fragment
        }
    }
}