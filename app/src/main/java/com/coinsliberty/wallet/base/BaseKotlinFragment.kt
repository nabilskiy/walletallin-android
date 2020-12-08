package com.coinsliberty.wallet.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.coinsliberty.wallet.BottomFragmant
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.otp.OtpDialog
import com.coinsliberty.wallet.ui.widgets.inputField.progressBarDialog.ProgressBarDialog
import com.coinsliberty.wallet.utils.extensions.bindDataTo

val dialogProgressBar = ProgressBarDialog.newInstance()

abstract class BaseKotlinFragment : Fragment() {

    abstract val layoutRes: Int

    abstract val viewModel: BaseViewModel

    abstract val navigator: BaseNavigator

    var navController: NavController? = null

    val dialog = OtpDialog.newInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDataTo(viewModel.onStartProgress, ::startProgress)
        bindDataTo(viewModel.onEndProgress, ::endProgress)
        viewModel.showError.observe(this, ::showError)
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
        bindDataTo(viewModel.showDialog, ::showDialog)
        bindDataTo(viewModel.restart, ::restartFragment)
    }

    private fun restartFragment(unit: Unit?) {
        onStart()
    }

    private fun showDialog(unit: Unit?) {
        dialog.apply {
            initListeners {
                login(it)
            }
        }.show(childFragmentManager, OtpDialog.TAG)
    }



    private fun login(otp: String) {
        viewModel.relogin(otp)
        dialog.dismiss()
    }

    private fun logout(b: Boolean?) {
        val rootFragment = ((parentFragment as NavHostFragment).parentFragment as? BottomFragmant)
        if(b == true) {
            viewModel.relogin()
        } else {
            if(rootFragment != null) {
                rootFragment.goToLogin()
            } else {
                navigator.goToLogin(navController)
            }
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