package com.example.coinsliberty.ui.signup

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.config.NavigationConfig
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
            navigator.goToProfile(navController)
        }
    }
}