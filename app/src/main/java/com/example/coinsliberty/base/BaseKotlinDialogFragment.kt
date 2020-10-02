package com.example.coinsliberty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.coinsliberty.utils.extensions.bindDataTo

abstract class BaseKotlinDialogFragment : DialogFragment() {

    abstract val layoutRes: Int

    abstract val viewModel: BaseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress) { }
        bindDataTo(viewModel.onEndProgress) { }
        onReceiveParams(arguments)
    }

    protected open fun onReceiveParams(arguments: Bundle?) {}

}