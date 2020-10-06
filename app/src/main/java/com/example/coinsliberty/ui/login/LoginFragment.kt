package com.example.coinsliberty.ui.login

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.config.NavigationConfig
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.view.*

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel = LoginViewModel()
    override val navigator: LoginNavigation = LoginNavigation()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.logout_icon)
        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE
        btnLoginUpdate.isEnabled = false

        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }
    }
}