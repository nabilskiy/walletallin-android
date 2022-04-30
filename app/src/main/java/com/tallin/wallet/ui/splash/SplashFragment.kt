package com.tallin.wallet.ui.splash

import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.ui.config.NavigationConfig
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModel()
    override val navigator: SplashNavigation = get()

    override fun onStart() {
        subscribeLiveData()
        super.onStart()

        GlobalScope.launch {
            delay(2000)
            viewModel.navigate()
        }
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
}