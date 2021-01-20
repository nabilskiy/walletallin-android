package com.coinsliberty.wallet.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.ui.config.NavigationConfig
import com.coinsliberty.wallet.utils.crypto.decryptData
import com.coinsliberty.wallet.utils.crypto.encryptData
import com.coinsliberty.wallet.utils.extensions.bindDataTo
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

    override fun onStart() {
        super.onStart()
        var data = "test"
        Log.e("!!!", data)
        data = encryptData(data) ?: ""
        Log.e("!!!", data)

        Log.e("!!!", decryptData(data) ?: "")
        lavCoinsLiberty.progress = 0.0f
        lavCoinsLiberty.playAnimation()
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