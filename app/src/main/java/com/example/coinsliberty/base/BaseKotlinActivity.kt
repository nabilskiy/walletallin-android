package com.example.coinsliberty.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.coinsliberty.ui.widgets.inputField.progressBarDialog.ProgressBarDialog
import com.example.coinsliberty.utils.extensions.bindDataTo

abstract class BaseKotlinActivity : FragmentActivity() {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress, ::startProgress)
        bindDataTo(viewModel.onEndProgress, ::endProgress)
    }
    private fun startProgress(unit: Unit?) {
        dialogProgressBar.show(supportFragmentManager, ProgressBarDialog.TAG)
    }

    private fun endProgress(unit: Unit?) {
        dialogProgressBar.dismiss()
    }
    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}