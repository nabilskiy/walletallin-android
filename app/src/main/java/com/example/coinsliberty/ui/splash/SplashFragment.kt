package com.example.coinsliberty.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.moneybee.ui.config.NavigationConfig
import com.example.moneybee.ui.splash.SplashNavigation
import com.example.moneybee.ui.splash.SplashViewModel
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class SplashFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_splash
    override val viewModel: SplashViewModel = SplashViewModel()
    override val navigator: SplashNavigation = SplashNavigation()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

        startFragmentAnimation()
    }


    private fun subscribeLiveData() {
    }

    private fun navigate(navigationConfig: NavigationConfig?) {
    }


    fun startFragmentAnimation() {
        lavCoinsLiberty.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            @SuppressLint("ShowToast")
            override fun onAnimationEnd(animation: Animator) {
                Toast.makeText(activity, "Finish", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }
}