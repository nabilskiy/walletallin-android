package com.example.coinsliberty.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.ui.config.NavigationConfig
import com.example.coinsliberty.utils.extensions.bindDataTo
import com.example.moneybee.ui.splash.SplashNavigation
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class SplashFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    override val navigator: SplashNavigation = get()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

        startFragmentAnimation()
    }


    private fun subscribeLiveData() {
        bindDataTo(viewModel.ldNavigate, ::navigate)
    }

    private fun navigate(navigationConfig: NavigationConfig) {
        when(navigationConfig) {
            is NavigationConfig.GO_TO_LOGIN -> navigator.goToLoginScreen(navController)
            is NavigationConfig.GO_TO_MAIN -> navigator.goToMainScreen(navController)
        }
    }


    fun startFragmentAnimation() {
        lavCoinsLiberty.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            @SuppressLint("ShowToast")
            override fun onAnimationEnd(animation: Animator) {
                viewModel.navigate()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }
}