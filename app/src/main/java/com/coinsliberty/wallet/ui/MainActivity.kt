package com.coinsliberty.wallet.ui
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.utils.extensions.setTransparentLightStatusBar
import com.coinsliberty.wallet.utils.extensions.setupFullScreen


class MainActivity : AppCompatActivity() {

    private var isActivePin: Boolean? = null

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
        isActivePin = true
    }

    override fun onStop() {
        super.onStop()
        Log.e("!!!", "onStop")
        isActivePin = true

    }
}
