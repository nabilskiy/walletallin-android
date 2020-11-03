package com.example.coinsliberty.ui.config

sealed class NavigationConfig {
    object GO_TO_LOGIN : NavigationConfig()
    object GO_TO_MAIN : NavigationConfig()
}