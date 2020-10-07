package com.example.coinsliberty.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinsliberty.R
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTransparentLightStatusBar()
        setupFullScreen()
        }
}
