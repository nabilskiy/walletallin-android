package com.tallin.wallet.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.tallin.wallet.R
import com.tallin.wallet.utils.extensions.setTransparentLightStatusBar
import com.tallin.wallet.utils.extensions.setupFullScreen
import java.lang.Exception
import java.text.SimpleDateFormat

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
    }


    override fun onPause() {
        super.onPause()

        try {

            val currentFragment =
                Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.label

            isActivePin =
                when(currentFragment){
                    "LoginFragment",
                    "SignUpFragment",
                    "SignUpBusinessFragment",
                    "ChooseWalletFragment" -> false
                    else -> showPin

                }
        } catch (e: Exception) {

        }


    }
}
