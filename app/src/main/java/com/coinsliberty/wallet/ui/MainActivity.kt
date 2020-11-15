package com.coinsliberty.wallet.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.utils.extensions.setTransparentLightStatusBar
import com.coinsliberty.wallet.utils.extensions.setupFullScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTransparentLightStatusBar()
        setupFullScreen()
        }
}
