package com.example.coinsliberty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.ui.widgets.inputField.progressBarDialog.ProgressBarDialog
import com.example.coinsliberty.utils.extensions.bindDataTo

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
        bindDataTo(viewModel.showError, ::showError)
        onReceiveParams(arguments)
    }

    private fun showError(s: String?) {
        if(s == null) return

        ErrorDialog.newInstance(s).show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun startProgress(unit: Unit?) {
        dialogProgressBar.show(parentFragmentManager, ProgressBarDialog.TAG)
    }

    private fun endProgress(unit: Unit?) {
        dialogProgressBar.dismiss()
    }

    protected open fun onReceiveParams(arguments: Bundle?) {}

}