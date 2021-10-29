package com.tallin.wallet.ui.login

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.ui.dialogs.ChangeLanguageDialog
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()
    override val navigator: LoginNavigation = get()

    var icon = R.drawable.ic_germany

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()
        

        //loginToolbar.ivToolbarRightIcon.setImageResource(R.drawable.logout_icon)
        ivLanguage.setOnClickListener {
            ChangeLanguageDialog.newInstance(icon)
                .apply {
                    initListeners {
                        icon = it.ico
                        dismiss()
                        changeLanguage()
                    }
                }.show(childFragmentManager, ChangeLanguageDialog.TAG)

        }
        //loginToolbar.ivAddPhoto.visibility = View.INVISIBLE

        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }
        tvForgotPassword.setOnClickListener { navigator.goToForgot(navController) }

        btnLoginUpdate.setOnClickListener {
            viewModel.login(loginEmailInput.getMyText(), loginPasswordInput.getMyText())
        }

//
    }


    private fun changeLanguage() {
        ivLanguage.setImageResource(icon)
    }

    private fun navigate() {
        navigator.goToContent(navController)
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultLogin, ::showResultLogin)
        bindDataTo(viewModel.resultGetProfile, ::showResultGetProfile)
    }

    private fun showResultLogin(b: Boolean?) {
        if (b == true) {
            viewModel.getProfile()
        }
    }

    private fun showResultGetProfile(b: Boolean?) {
        if (b == true) {
            navigate()
        }
    }
}

