package com.example.coinsliberty.ui.login

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.view.*

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel = LoginViewModel()
    override val navigator: LoginNavigation = LoginNavigation()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

        loginToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.logout_icon)
        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE
        btnLoginUpdate.setOnClickListener {
            navigate()
        }
    }


    private fun subscribeLiveData() {
    }

    private fun navigate() {
        navigator.goToContent(navController)
    }

}