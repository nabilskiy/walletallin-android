package com.tallin.wallet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tallin.wallet.dialogs.ErrorDialog
import com.tallin.wallet.ui.widgets.inputField.progressBarDialog.ProgressBarDialog
import com.tallin.wallet.utils.extensions.bindDataTo

abstract class BaseKotlinDialogFragment : DialogFragment() {

    abstract val layoutRes: Int

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress, ::startProgress)
        bindDataTo(viewModel.onEndProgress, ::endProgress)
        viewModel.showError.observe(this, ::showError)
        onReceiveParams(arguments)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRequest()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRequest()
    }

    private fun showError(s: String?) {
        if(s == null) return

        ErrorDialog.newInstance(s).show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun startProgress(unit: Unit?) {
        //todo check
        dialogProgressBar.show(parentFragmentManager, ProgressBarDialog.TAG)
    }

    private fun endProgress(unit: Unit?) {
        dialogProgressBar.dismiss()
    }

    protected open fun onReceiveParams(arguments: Bundle?) {}

}