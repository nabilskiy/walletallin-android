package com.example.coinsliberty.ui.login

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.config.NavigationConfig
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

        loginToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.logout_icon)
        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE

        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }

        btnLoginUpdate.setOnClickListener {
            navigate()
            }
        }

    private fun navigate() {
        navigator.goToContent(navController)
    }

}

