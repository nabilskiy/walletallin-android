package com.coinsliberty.wallet.ui
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.utils.extensions.setTransparentLightStatusBar
import com.coinsliberty.wallet.utils.extensions.setupFullScreen
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var isActivePin: Boolean? = null
    var showPin: Boolean = true
    //var currentFragment: Fragment = getActivity().getFragmentManager().findFragmentById(R.id.navHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            setTransparentLightStatusBar()
            setupFullScreen()
        } catch (e: Exception) {

        }

    }


    override fun onStart() {
        super.onStart()
        try {
            if (isActivePin == true) {
                Log.e("!!!", "isActive")
                Navigation.findNavController(this, R.id.navHostFragment)
                    .navigate(R.id.action_pin_code)
            }
        } catch (e: Exception) {

        }

        changeNavigationBarColor(
            ContextCompat.getColor(
                this,
                R.color.balance_header_color
            )
        )
    }

    fun changeNavigationBarColor(color: Int) {
        window?.navigationBarColor = color
    }


    override fun onPause() {
        super.onPause()

        try {
            val currentFragment = Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.label
            isActivePin = currentFragment != "LoginFragment" && showPin
        } catch (e: Exception) {

        }



    }

    override fun onStop() {
        super.onStop()

        try {
            val currentFragment = Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.label
            isActivePin = currentFragment != "LoginFragment" && showPin
        } catch (e: Exception) {

        }

    }
}
