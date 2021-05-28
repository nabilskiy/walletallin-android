package com.tallin.wallet.base

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.tallin.wallet.R
import java.lang.ref.WeakReference

abstract class BaseNavigator {

    var activity: WeakReference<FragmentActivity?> = WeakReference(null)

    fun attach(activity: FragmentActivity) {
        this.activity = WeakReference(activity)
    }

    fun release() {
        this.activity = WeakReference(null)
    }

    fun goToLogin(navHostController: NavController?) {
        navHostController?.navigate(R.id.action_login)
    }


    fun goToProfile(navHostController: NavController?) {
        navHostController?.navigate(R.id.action_bottomTabNavigator_to_profileFragment)
    }

    fun back() {
        this.activity.get()?.onBackPressed()
    }
}