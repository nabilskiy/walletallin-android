package com.coinsliberty.wallet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.Navigation
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.dialogs.exchangeComingSoon.ExchangeDialog
import com.coinsliberty.wallet.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.activity_bottom.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class BottomFragmant : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.activity_bottom
    override val viewModel: StubViewModel by viewModel()
    override val navigator: StubNavigator = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dialog = ExchangeDialog.newInstance()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.wallet -> {
                    Navigation.findNavController(requireActivity(), R.id.frameLayout).navigate(R.id.action_global_my_wallet)
                    true
                }
                 R.id.exchange -> {
                     Navigation.findNavController(requireActivity(), R.id.frameLayout).navigate(R.id.action_global_exchange)
                     dialog.show(childFragmentManager, ExchangeDialog.TAG)
                     dialog.initListeners {
                         if(it) bottomNavigation.selectedItemId = R.id.wallet
                     }
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