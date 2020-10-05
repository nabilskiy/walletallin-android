package com.example.coinsliberty.ui.signup

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.config.NavigationConfig



class SignUpFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel = SignUpViewModel()
    override val navigator: SignUpNavigation = SignUpNavigation()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

    }


    private fun subscribeLiveData() {
    }

    private fun navigate(navigationConfig: NavigationConfig?) {
    }

}