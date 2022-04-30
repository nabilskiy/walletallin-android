package com.tallin.wallet.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.tallin.wallet.R
//import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.utils.extensions.setTransparentLightStatusBar
import com.tallin.wallet.utils.extensions.setupFullScreen
import java.lang.Exception
//import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    private var isActivePin: Boolean? = null
    var showPin: Boolean = true
    //private val sharedPreferencesProvider: SharedPreferencesProvider = get()

    var skipPin: Boolean = false
    //var postPinFragment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            setTransparentLightStatusBar()
            setupFullScreen()
        } catch (e: Exception) {

        }

    }


    /*override fun onStart() {
        super.onStart()
        try {
            if (isActivePin == true) {
                Log.e("!!!", "isActive")
                Navigation.findNavController(this, R.id.navHostFragment)
                    .navigate(R.id.action_pin_code)
            }
        } catch (e: Exception) {

        }
    }*/


    override fun onPause() {
        super.onPause()

        try {

            val currentFragment =
                Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.label
            val currentChildrenFragment =
                Navigation.findNavController(this, R.id.frameLayout).currentDestination?.label
            println(currentFragment)
            isActivePin =
                when(currentFragment){
                    "LoginFragment",
                    "SignUpFragment",
                    "SignUpBusinessFragment",
                    "ChooseWalletFragment"-> false

                    "BottomActivityy" -> if (skipPin){
                        skipPin = false; false
                    } else {
                       /* when (currentChildrenFragment){
                            "OrderPreviewFragment" -> postPinFragment = "OrderPreviewFragment"
                        }*/
                        showPin
                    }
                    else -> showPin//if (!sharedPreferencesProvider.getToken().isNullOrEmpty()) showPin else false

                }
        } catch (e: Exception) {
        }
    }
}
