package com.example.coinsliberty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.base.BaseNavigator
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen
import com.example.coinsliberty.utils.stub.StubViewModel
import com.example.moneybee.utils.stub.StubNavigator
import kotlinx.android.synthetic.main.activity_bottom.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class BottomFragmant : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.activity_bottom
    override val viewModel: StubViewModel by viewModel()
    override val navigator: StubNavigator = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wallet -> {
                    Navigation.findNavController(requireActivity(), R.id.frameLayout).navigate(R.id.action_global_my_wallet)
                    true
                }
                R.id.exchange -> {
                    Navigation.findNavController(requireActivity(), R.id.frameLayout).navigate(R.id.action_global_exchange)
                    true
                }
                R.id.setting -> {
                    Navigation.findNavController(requireActivity(), R.id.frameLayout).navigate(R.id.action_global_settings)
                    true
                }
                else -> false
            }
        }
    }

    fun goToLogin() {
        navigator.goToLogin(navController)
    }
    fun goToProfile() {
        navigator.goToProfile(navController)
    }

}