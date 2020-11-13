package com.example.coinsliberty.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.coinsliberty.BottomFragmant
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.ui.MainActivity
import com.example.coinsliberty.ui.widgets.inputField.progressBarDialog.ProgressBarDialog
import com.example.coinsliberty.utils.extensions.bindDataTo

val dialogProgressBar = ProgressBarDialog.newInstance()

abstract class BaseKotlinFragment : Fragment() {

    abstract val layoutRes: Int

    abstract val viewModel: BaseViewModel

    abstract val navigator: BaseNavigator

    var navController: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
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

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected open fun onReceiveParams(arguments: Bundle?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.baseLogout, ::logout)
    }

    private fun logout(b: Boolean?) {
        if(parentFragment != null) {
            (parentFragment as BottomFragmant).goToLogin()
        } else {
            navigator.goToLogin(navController)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigator.attach(requireActivity())
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.release()
    }
}