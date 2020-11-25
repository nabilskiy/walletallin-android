package com.coinsliberty.wallet.ui.login

import android.os.Bundle
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.dialogs.forgetPassword.ForgotPassDialog
import com.coinsliberty.wallet.ui.dialogs.ChangeLanguageDialog
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()
    override val navigator: LoginNavigation = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()

        loginToolbar.ivToolbarIconLeft.visibility = View.GONE
        loginToolbar.ivToolbarRightIcon.setImageResource(R.drawable.logout_icon)
        loginToolbar.ivToolbarIconLeft.visibility = View.GONE

        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE

        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }

        btnLoginUpdate.setOnClickListener {
            viewModel.login(loginEmailInput.getMyText(), loginPasswordInput.getMyText(), if(ifcCode.getMyText().isEmpty()) null else ifcCode.getMyText())
            }

        tvForgotPassword.setOnClickListener {
            ForgotPassDialog.newInstance()
                .apply { initListeners {
                    //if(!it) ErrorDialog.newInstance("Пустые поля").show(childFragmentManager, ErrorDialog.TAG)
                    dismiss()

                } }
                .show(childFragmentManager, ForgotPassDialog.TAG)
        }
    }


    private fun navigate() {
        navigator.goToContent(navController)
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }

    private fun showResult(b: Boolean?) {
        if(b == true) {
            navigate()
        } else {
            ifcCode.visible()
        }
    }

}

