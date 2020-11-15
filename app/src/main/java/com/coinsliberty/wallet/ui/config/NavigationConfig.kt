package com.coinsliberty.wallet.ui.config

sealed class NavigationConfig {
    object GO_TO_LOGIN : NavigationConfig()
    object GO_TO_MAIN : NavigationConfig()
}