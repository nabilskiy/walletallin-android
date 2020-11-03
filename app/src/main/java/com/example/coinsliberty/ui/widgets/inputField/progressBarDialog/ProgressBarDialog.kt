package com.example.coinsliberty.ui.widgets.inputField.progressBarDialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
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