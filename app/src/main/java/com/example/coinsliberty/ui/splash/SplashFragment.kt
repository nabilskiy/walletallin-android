package com.example.coinsliberty.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
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
    }

    private fun navigate() {
        navigator.goToLoginScreen(navController)
    }


    fun startFragmentAnimation() {
        lavCoinsLiberty.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            @SuppressLint("ShowToast")
            override fun onAnimationEnd(animation: Animator) {
                    navigate()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }
}