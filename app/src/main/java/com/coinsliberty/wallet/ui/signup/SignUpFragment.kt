package com.coinsliberty.wallet.ui.signup

import android.os.Bundle
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModel()
    override val navigator: SignUpNavigation = get()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLogin.setOnClickListener { activity?.onBackPressed() }
        btnSignUp.setOnClickListener {
            viewModel.signUp(ifcEmail.getMyText(), ifcSignUpPassword.getMyText(), ifcFirstName.getMyText(), ifcLastName.getMyText())
        }
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }

    private fun showResult(b: Boolean?) {
        if(b == true) {
            activity?.onBackPressed()
        }
    }
}