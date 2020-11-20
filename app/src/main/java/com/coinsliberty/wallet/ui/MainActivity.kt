package com.coinsliberty.wallet.ui
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.utils.extensions.setTransparentLightStatusBar
import com.coinsliberty.wallet.utils.extensions.setupFullScreen


class MainActivity : AppCompatActivity() {

    private var isActivePin: Boolean? = null
    //var currentFragment: Fragment = getActivity().getFragmentManager().findFragmentById(R.id.navHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTransparentLightStatusBar()
        setupFullScreen()

    }

    override fun onStart() {
        super.onStart()
        if (isActivePin == true) {
            Log.e("!!!", "isActive")
            Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.action_pin_code)
        }
    }


    override fun onPause() {
        super.onPause()
        Log.e("!!!", "onPause")
        Log.e("!!!a", Navigation.findNavController(this, R.id.navHostFragment).currentDestination.toString())
        val currentFragment: String = Navigation.findNavController(this, R.id.navHostFragment).currentDestination.toString()
        val loginFragment: String = "Destination(com.coinsliberty.wallet:id/loginFragment) label=LoginFragment class=com.coinsliberty.wallet.ui.login.LoginFragment"
        if(currentFragment != loginFragment) {
            isActivePin = true
        }

    }

    override fun onStop() {
        super.onStop()
        Log.e("!!!", "onStop")
        val currentFragment: String = Navigation.findNavController(this, R.id.navHostFragment).currentDestination.toString()
        val loginFragment: String = "Destination(com.coinsliberty.wallet:id/loginFragment) label=LoginFragment class=com.coinsliberty.wallet.ui.login.LoginFragment"
        if(currentFragment != loginFragment) {
            isActivePin = true
        }

    }
}
